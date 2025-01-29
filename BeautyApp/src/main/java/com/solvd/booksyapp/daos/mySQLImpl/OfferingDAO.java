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
    private static final String GET_BY_ID = "SELECT * FROM Offerings WHERE id = ?";
    private static final String GET_BY_EMPLOYEE_ID = "SELECT * FROM Offerings WHERE employee_id = ?";
    private static final String GET_BY_BUSINESS_ID = "SELECT * FROM Offerings o " +
                    "LEFT JOIN Employees e ON o.employee_id = e.id " +
                    "WHERE e.business_id = ?";
    private static final String GET_BY_SERVICE_ID = "SELECT * FROM Offerings WHERE service_id = ?";
    private static final String SAVE = "INSERT INTO Offerings (employee_id, service_id, price) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE Offerings SET employee_id = ?, service_id = ?, price = ? WHERE id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Offerings WHERE id = ?";

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
    public List<Offering> getByServiceId(Long serviceId) {
        List<Offering> offerings = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_SERVICE_ID)) {
            statement.setLong(1, serviceId);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    offerings.add(getMappedOffering(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting offerings for service_id {} : {}", serviceId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return offerings;
    }

    @Override
    public Offering getById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedOffering(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting offering with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Offering save(Offering entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE)) {
            statement.setLong(1, entity.getEmployeeId());
            statement.setLong(2, entity.getServiceId());
            statement.setBigDecimal(3, entity.getPrice());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving offering failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
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
    public Offering update(Offering entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setLong(1, entity.getEmployeeId());
            statement.setLong(2, entity.getServiceId());
            statement.setBigDecimal(3, entity.getPrice());
            statement.setLong(4, entity.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no offering found with id: " + entity.getId());
            }

            return entity;
        } catch (SQLException ex) {
            logger.error("Error updating offering with id {}: {}", entity.getId(), ex);
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
            logger.error("Error removing offering with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private Offering getMappedOffering(ResultSet resultSet) throws SQLException {
        Offering offering = new Offering();
        offering.setId(resultSet.getLong("id"));
        offering.setEmployeeId(resultSet.getLong("employee_id"));
        offering.setServiceId(resultSet.getLong("service_id"));
        offering.setPrice(resultSet.getBigDecimal("price"));
        return offering;
    }
}
