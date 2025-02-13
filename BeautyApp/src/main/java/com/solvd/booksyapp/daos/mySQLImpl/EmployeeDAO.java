package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IEmployeeDAO;
import com.solvd.booksyapp.models.Employee;
import com.solvd.booksyapp.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IEmployeeDAO {
    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class.getName());
    private static final String GET_BY_BUSINESS_ID = "SELECT * FROM Employees WHERE business_id = ?";
    private static final String GET_BY_USER_ID = "SELECT * FROM Employees WHERE user_id = ?";
    private static final String GET_BY_RATING_ABOVE = "SELECT * FROM Employees WHERE rating > ?";
    private static final String GET_BY_RATING_RANGE = "SELECT * FROM Employees WHERE rating BETWEEN ? AND ?";
    private static final String COUNT_BY_BUSINESS_ID = "SELECT COUNT(*) FROM Employees WHERE business_id = ?";
    private static final String UPDATE_RATING = "UPDATE Employees SET rating = ? WHERE id = ?";
    private static final String GET_BY_ID = "SELECT * FROM Employees WHERE id = ?";
    private static final String SAVE = "INSERT INTO Employees (user_id, description, rating, business_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Employees SET description = ?, rating = ?, business_id = ? WHERE id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Employees WHERE id = ?";

    @Override
    public List<Employee> getEmployeesByBusinessId(Long businessId) {
        List<Employee> employees = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_BUSINESS_ID)) {
            statement.setLong(1, businessId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(getMappedEmployee(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching employees by business ID {} : {}", businessId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return employees;
    }

    @Override
    public Employee getByUserId(Long userId) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_USER_ID)) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedEmployee(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting employee with user id {} : {}", userId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Employee> getByRatingAbove(BigDecimal rating) {
        List<Employee> employees = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_RATING_ABOVE)) {
            statement.setBigDecimal(1, rating);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(getMappedEmployee(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching employees rating above {} : {}", rating, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return employees;
    }

    @Override
    public List<Employee> getByRatingRange(BigDecimal minRating, BigDecimal maxRating) {
        List<Employee> employees = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_RATING_RANGE)) {
            statement.setBigDecimal(1, minRating);
            statement.setBigDecimal(2, maxRating);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(getMappedEmployee(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching employees with rating between {} and {} : {}", minRating, maxRating, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return employees;
    }

    @Override
    public int countByBusinessId(Long businessId) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(COUNT_BY_BUSINESS_ID)) {
            statement.setLong(1, businessId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error counting employees by business ID {} : {}", businessId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return 0;
    }

    @Override
    public boolean updateRating(Long id, BigDecimal rating) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_RATING)) {
            statement.setBigDecimal(1, rating);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.error("Error updating rating for employee with ID {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return false;
    }

    @Override
    public Employee getById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedEmployee(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting employee with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void save(Employee entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getDescription());
            statement.setBigDecimal(3, entity.getRating());
            statement.setLong(4, entity.getBusinessId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Saving employee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error saving employee {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void update(Employee entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getDescription());
            statement.setBigDecimal(2, entity.getRating());
            statement.setLong(3, entity.getBusinessId());
            statement.setLong(4, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no employee found with id: " + entity.getId());
            }
        } catch (SQLException ex) {
            logger.error("Error updating employee {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void removeById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing employee by ID {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private Employee getMappedEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();

        employee.setId(resultSet.getLong("id"));
        employee.setUserId(resultSet.getLong("user_id"));
        employee.setDescription(resultSet.getString("description"));
        employee.setRating(resultSet.getBigDecimal("rating"));
        employee.setBusinessId(resultSet.getLong("business_id"));
        return employee;
    }
}
