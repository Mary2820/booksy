package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IServiceDAO;
import com.solvd.booksyapp.models.Service;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO implements IServiceDAO {
    private static final Logger logger = LogManager.getLogger(ServiceDAO.class.getName());

    @Override
    public List<Service> getByCategoryId(Long categoryId) {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Services WHERE category_id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, categoryId);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    services.add(getMappedService(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting services with category_id: {}", categoryId, ex);
        }
        return services;
    }

    @Override
    public Service getByName(String name) {
        String sql = "SELECT * FROM Users WHERE name = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedService(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting service with name {}:  {}", name, ex);
        }
        return null;
    }

    @Override
    public Service getById(Long id) {
        String sql = "SELECT * FROM Services WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedService(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting service with id {}: {}", id, ex);
        }
        return null;
    }

    @Override
    public Service save(Service entity) {
        String sql = "INSERT INTO Services (category_id, name, description, duration) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getCategoryId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getDuration());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving service failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        }
        catch (SQLException ex) {
            logger.error("Error saving service: {}", entity, ex);
        }
        return null;
    }

    @Override
    public Service update(Service entity) {
        String sql = "UPDATE Services SET name = ?, description = ?, duration = ? WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getDuration());
            statement.setLong(7, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no service found with id: " + entity.getId());
            }

            return entity;

        } catch (SQLException ex) {
            logger.error("Error updating service with id {}: {}", entity.getId(), ex);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        String sql = "DELETE FROM Services WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing service with id: {}", id, ex);
        }
    }

    private Service getMappedService(ResultSet resultSet) throws SQLException {
        Service service = new Service();

        service.setId(resultSet.getLong("id"));
        service.setCategoryId(resultSet.getLong("category_id"));
        service.setName(resultSet.getString("name"));
        service.setDescription(resultSet.getString("description"));
        service.setDuration(resultSet.getInt("duration"));

        return service;
    }
}
