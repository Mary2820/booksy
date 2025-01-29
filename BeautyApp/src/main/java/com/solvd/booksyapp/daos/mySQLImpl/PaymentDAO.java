package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IPaymentDAO;
import com.solvd.booksyapp.models.Payment;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PaymentDAO extends AbstractMySQLDAO implements IPaymentDAO {
    private static final Logger logger = LogManager.getLogger(PaymentDAO.class.getName());
    private static final String GET_BY_APPOINTMENT_ID = "SELECT * FROM Payments WHERE appointment_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM Payments WHERE id = ?";
    private static final String SAVE = "INSERT INTO Payments (amount, payment_date, payment_status, appointment_id)" +
            " VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Payments SET amount = ?, payment_date = ?, payment_status = ?," +
            " appointment_id = ? WHERE id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Payments WHERE id = ?";

    @Override
    public Payment getByAppointmentId(Long appointmentId) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_APPOINTMENT_ID)) {
            statement.setLong(1, appointmentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedPayment(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting payment with appointment_id {} : {}", appointmentId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Payment getById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedPayment(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting payment with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Payment save(Payment entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE)) {
            statement.setBigDecimal(1, entity.getAmount());
            statement.setDate(2, Date.valueOf(entity.getPaymentDate()));
            statement.setString(3, entity.getStatus());
            statement.setLong(4, entity.getAppointmentId());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving payment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        } catch (SQLException ex) {
            logger.error("Error saving payment {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Payment update(Payment entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setBigDecimal(1, entity.getAmount());
            statement.setDate(2, Date.valueOf(entity.getPaymentDate()));
            statement.setString(3, entity.getStatus());
            statement.setLong(4, entity.getAppointmentId());
            statement.setLong(5, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no payment found with id: " + entity.getId());
            }

            return entity;
        } catch (SQLException ex) {
            logger.error("Error updating payment with id {}: {}", entity.getId(), ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing payment with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private Payment getMappedPayment(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();

        payment.setId(resultSet.getLong("id"));
        payment.setAmount(resultSet.getBigDecimal("amount"));
        payment.setPaymentDate(resultSet.getDate("payment_date").toLocalDate());
        payment.setStatus(resultSet.getString("payment_status"));
        payment.setAppointmentId(resultSet.getLong("appointment_id"));

        return payment;
    }
}
