package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IUserDAO;
import com.solvd.booksyapp.models.User;
import com.solvd.booksyapp.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractMySQLDAO implements IUserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class.getName());
    private static final String GET_BY_FULL_NAME = "SELECT * FROM Users WHERE first_name = ? AND last_name = ?";
    private static final String GET_BY_EMAIL = "SELECT * FROM Users WHERE email = ?";
    private static final String GET_BY_ROLE_ID = "SELECT * FROM Users WHERE role_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String SAVE = "INSERT INTO Users (first_name, last_name, email, phone, password, role_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, phone = ?, password = ?, role_id = ? WHERE id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Users WHERE id = ?";

    @Override
    public User getUserByFullName(String firstName, String lastName) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_FULL_NAME)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting user with full name {} {} : {}", firstName, lastName, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting user with email {} : {}", email, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<User> getByRoleId(Long roleId) {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ROLE_ID)) {
            statement.setLong(1, roleId);

            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    users.add(getMappedUser(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting users with role_id {} : {}", roleId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return users;
    }

    @Override
    public User getById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting user with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void save(User entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
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
        } catch (SQLException ex) {
            logger.error("Error saving user {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        };
    }

    @Override
    public void update(User entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
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

        } catch (SQLException ex) {
            logger.error("Error updating user with id {}: {}", entity.getId(), ex);
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
            logger.error("Error removing user with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
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
