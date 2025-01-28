package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IUserDAO;
import com.solvd.booksyapp.models.User;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractMySQLDAO implements IUserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class.getName());

    @Override
    public User getUserByFullName(String firstName, String lastName) {
        String sql = "SELECT * FROM Users WHERE first_name = ? AND last_name = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting user with full name {} {} : {}", firstName, lastName, ex);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting user with email {} : {}", email, ex);
        }
        return null;
    }

    @Override
    public List<User> getByRoleId(Long roleId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE role_id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, roleId);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                users.add(getMappedUser(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting users with role_id {} : {}", roleId, ex);
        }
        return users;
    }

    @Override
    public User getById(Long id) {
        String sql = "SELECT * FROM Users WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting user with id {} : {}", id, ex);
        }
        return null;
    }

    @Override
    public User save(User entity) {
        String sql = "INSERT INTO Users (first_name, last_name, email, phone, password, role_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPhone());
            statement.setString(5, entity.getPassword());
            statement.setLong(6, entity.getRoleId());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        }
        catch (SQLException ex) {
            logger.error("Error saving user {} : {}", entity, ex);
        }
        return null;
    }

    @Override
    public User update(User entity) {
        String sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, phone = ?, password = ?, role_id = ? " +
                "WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPhone());
            statement.setString(5, entity.getPassword());
            statement.setLong(6, entity.getRoleId());
            statement.setLong(7, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no user found with id: " + entity.getId());
            }

            return entity;

        } catch (SQLException ex) {
            logger.error("Error updating user with id {}: {}", entity.getId(), ex);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        String sql = "DELETE FROM Users WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing user with id {} : {}", id, ex);
        }
    }

    private User getMappedUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        user.setPassword(resultSet.getString("password"));
        user.setRoleId(resultSet.getLong("role_id"));

        return user;
    }
}
