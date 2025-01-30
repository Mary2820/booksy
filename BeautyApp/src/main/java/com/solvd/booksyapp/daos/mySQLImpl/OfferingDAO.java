package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IOfferingDAO;
import com.solvd.booksyapp.models.Offering;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfferingDAO extends AbstractMySQLDAO implements IOfferingDAO {
    private static final Logger logger = LogManager.getLogger(OfferingDAO.class.getName());
    private static final String GET_BY_EMPLOYEE_ID = "SELECT * FROM Offerings WHERE employee_id = ?";
    private static final String GET_BY_BUSINESS_ID = "SELECT * FROM Offerings o " +
                    "LEFT JOIN Employees e ON o.employee_id = e.id " +
                    "WHERE e.business_id = ?";
    private static final String GET_BY_PROCEDURE_ID = "SELECT * FROM Offerings WHERE procedure_id = ?";
    private static final String GET_BY_EMPLOYEE_AND_PROCEDURE = "SELECT * FROM Offerings WHERE employee_id = ?" +
            " AND procedure_id = ?;";
    private static final String SAVE = "INSERT INTO Offerings (employee_id, procedure_id, price) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE Offerings SET price = ? WHERE employee_id = ?, procedure_id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Offerings WHERE employee_id = ?, procedure_id = ?";

    @Override
    public List<Offering> getByEmployeeId(Long employeeId) {
        List<Offering> offerings = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_EMPLOYEE_ID)) {
            statement.setLong(1, employeeId);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    offerings.add(getMappedOffering(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting offerings for employee_id {} : {}", employeeId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return offerings;
    }

    @Override
    public List<Offering> getByBusinessId(Long businessId) {
        List<Offering> offerings = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_BUSINESS_ID)) {
            statement.setLong(1, businessId);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    offerings.add(getMappedOffering(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting offerings for business_id {} : {}", businessId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return offerings;
    }

    @Override
    public List<Offering> getByProcedureId(Long procedureId) {
        List<Offering> offerings = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_PROCEDURE_ID)) {
            statement.setLong(1, procedureId);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    offerings.add(getMappedOffering(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting offerings for procedure_id {} : {}", procedureId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return offerings;
    }

    @Override
    public Offering getByEmployeeIdAndProcedureId(Long employeeId, Long procedureId) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_EMPLOYEE_AND_PROCEDURE)) {
            statement.setLong(1, employeeId);
            statement.setLong(2, procedureId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedOffering(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting offering with employeeId {} and procedureId {}: {}", employeeId, procedureId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Offering save(Offering entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getEmployeeId());
            statement.setLong(2, entity.getProcedureId());
            statement.setBigDecimal(3, entity.getPrice());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving offering failed, no rows affected.");
            }

            return entity;
        } catch (SQLException ex) {
            logger.error("Error saving offering {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Offering updatePrice(Offering offering) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setBigDecimal(1, offering.getPrice());
            statement.setLong(2, offering.getEmployeeId());
            statement.setLong(3, offering.getProcedureId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no offering found");
            }

            return offering;
        } catch (SQLException ex) {
            logger.error("Error updating offering with employee id {} and procedure id {} : {}",
                    offering.getEmployeeId(), offering.getProcedureId(), ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void remove(Long employeeId, Long procedureId) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID)) {
            statement.setLong(1, employeeId);
            statement.setLong(2, procedureId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing offering with employee id {} and procedure id {} : {}",
                    employeeId, procedureId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private Offering getMappedOffering(ResultSet resultSet) throws SQLException {
        Offering offering = new Offering();

        offering.setEmployeeId(resultSet.getLong("employee_id"));
        offering.setProcedureId(resultSet.getLong("procedure_id"));
        offering.setPrice(resultSet.getBigDecimal("price"));
        return offering;
    }
}
