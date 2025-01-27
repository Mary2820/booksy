package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IUserRoleDAO;
import com.solvd.booksyapp.models.User;
import com.solvd.booksyapp.models.UserRole;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleDAO implements IUserRoleDAO {
    private static final Logger logger = LogManager.getLogger(UserRoleDAO.class.getName());

    @Override
    public UserRole getByName(String name) {
        String sql = "SELECT * FROM User_Roles WHERE name = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUserRole(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting user role with name: {} {}", name, ex);
        }
        return null;
    }

    @Override
    public UserRole getById(Long id) {
        String sql = "SELECT * FROM User_Roles WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUserRole(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting user role with id: {}", id, ex);
        }
        return null;
    }

    @Override
    public UserRole save(UserRole entity) {
        String sql = "INSERT INTO User_Roles (name) VALUES (?)";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getName());


            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving user role failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        }
        catch (SQLException ex) {
            logger.error("Error saving user role: {}", entity, ex);
        }
        return null;
    }

    @Override
    public UserRole update(UserRole entity) {
        String sql = "UPDATE User_Roles SET name = ? WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no user role found with id: " + entity.getId());
            }

            logger.info("User role with id {} successfully updated.", entity.getId());
            return entity;

        } catch (SQLException ex) {
            logger.error("Error updating user role with id {}: {}", entity.getId(), ex);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        String sql = "DELETE FROM User_Roles WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing user role with id: {}", id, ex);
        }
    }

    private UserRole getMappedUserRole(ResultSet resultSet) throws SQLException {
        UserRole userRole = new UserRole();

        userRole.setId(resultSet.getLong("id"));
        userRole.setName(resultSet.getString("name"));

        return userRole;
    }
}
