package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.INotificationDAO;
import com.solvd.booksyapp.models.Notification;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO extends AbstractMySQLDAO implements INotificationDAO {
    private static final Logger logger = LogManager.getLogger(NotificationDAO.class.getName());
    private static final String GET_BY_CLIENT_ID = "SELECT * FROM Notifications n " +
            "LEFT JOIN Appointments a ON n.appointment_id = a.id " +
            "WHERE a.client_id = ?";
    private static final String GET_BY_EMPLOYEE_ID = "SELECT * FROM Notifications n " +
            "LEFT JOIN Appointments a ON n.appointment_id = a.id " +
            "WHERE a.employee_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM Notifications WHERE id = ?";
    private static final String SAVE = "INSERT INTO Notifications (message, appointment_id, status) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE Notifications SET message = ?, appointment_id = ?, status = ? WHERE id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Notifications WHERE id = ?";

    @Override
    public List<Notification> getByClientId(Long clientId) {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_CLIENT_ID)) {
            statement.setLong(1, clientId);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notifications.add(getMappedNotification(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting notifications for client_id {} : {}", clientId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return notifications;
    }

    @Override
    public List<Notification> getByEmployeeId(Long employeeId) {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_EMPLOYEE_ID)) {
            statement.setLong(1, employeeId);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notifications.add(getMappedNotification(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting notifications for employee_id {} : {}", employeeId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return notifications;
    }

    @Override
    public Notification getById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedNotification(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting notification with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Notification save(Notification entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE)) {
            statement.setString(1, entity.getMessage());
            statement.setLong(2, entity.getAppointmentId());
            statement.setString(3, entity.getStatus());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving notification failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        } catch (SQLException ex) {
            logger.error("Error saving notification {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Notification update(Notification entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getMessage());
            statement.setLong(2, entity.getAppointmentId());
            statement.setString(3, entity.getStatus());
            statement.setLong(4, entity.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no notification found with id: " + entity.getId());
            }

            return entity;
        } catch (SQLException ex) {
            logger.error("Error updating notification with id {}: {}", entity.getId(), ex);
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
            logger.error("Error removing notification with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private Notification getMappedNotification(ResultSet resultSet) throws SQLException {
        Notification notification = new Notification();

        notification.setId(resultSet.getLong("id"));
        notification.setMessage(resultSet.getString("message"));
        notification.setAppointmentId(resultSet.getLong("appointment_id"));
        notification.setStatus(resultSet.getString("status"));

        return notification;
    }
}
