package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IEmployeeDAO;
import com.solvd.booksyapp.models.Employee;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IEmployeeDAO {
    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class.getName());

    @Override
    public List<Employee> getEmployeesByBusinessId(Long businessId) {
        String sql = "SELECT * FROM Employees WHERE business_id = ?";
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, businessId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(getMappedEmployee(resultSet));
                }
            }

        } catch (SQLException ex) {
            logger.error("Error fetching employees by business ID: {}", businessId, ex);
        }
        return employees;
    }

    @Override
    public List<Employee> getByRatingAbove(BigDecimal rating) {
        String sql = "SELECT * FROM Employees WHERE rating > ?";
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, rating);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(getMappedEmployee(resultSet));
                }
            }

        } catch (SQLException ex) {
            logger.error("Error fetching employees rating above: {}", rating,  ex);
        }
        return employees;
    }

    @Override
    public List<Employee> getByRatingRange(BigDecimal minRating, BigDecimal maxRating) {
        String sql = "SELECT * FROM Employees WHERE rating BETWEEN ? AND ?";
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, minRating);
            statement.setBigDecimal(2, maxRating);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(getMappedEmployee(resultSet));
                }
            }

        } catch (SQLException ex) {
            logger.error("Error fetching employees with rating between {} and {}", minRating, maxRating, ex);
        }

        return employees;
    }

    @Override
    public int countByBusinessId(Long businessId) {
        String sql = "SELECT COUNT(*) FROM Employees WHERE business_id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, businessId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException ex) {
            logger.error("Error counting employees by business ID: {}", businessId, ex);
        }
        return 0;
    }

    @Override
    public boolean updateRating(Long id, BigDecimal rating) {
        String sql = "UPDATE Employees SET rating = ? WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, rating);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            logger.error("Error updating rating for employee with ID: {}", id, ex);
        }
        return false;
    }

    @Override
    public Employee getById(Long id) {
        String sql = "SELECT * FROM Employees WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedEmployee(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting employee with id: {}", id, ex);
        }
        return null;
    }

    @Override
    public Employee save(Employee entity) {
        String sql = "INSERT INTO Employees (user_id, description, rating, business_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

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
            return entity;

        } catch (SQLException ex) {
            logger.error("Error saving employee: {}", entity, ex);
        }
        return null;
    }

    @Override
    public Employee update(Employee entity) {
        String sql = "UPDATE Employees SET user_id = ?, description = ?, rating = ?, business_id = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getDescription());
            statement.setBigDecimal(3, entity.getRating());
            statement.setLong(4, entity.getBusinessId());
            statement.setLong(5, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no employee found with id: " + entity.getId());
            }

            return entity;

        } catch (SQLException ex) {
            logger.error("Error updating employee: {}", entity, ex);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        String sql = "DELETE FROM Employees WHERE id = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            logger.error("Error removing employee by ID: {}", id, ex);
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
