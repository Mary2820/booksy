package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IAppointmentDAO;
import com.solvd.booksyapp.models.Appointment;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO implements IAppointmentDAO {
    private static final Logger logger = LogManager.getLogger(AppointmentDAO.class.getName());

    @Override
    public List<Appointment> getByClientId(Long clientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE client_id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, clientId);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    appointments.add(getMappedAppointment(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting appointments with client id: {}", clientId, ex);
        }
        return appointments;
    }

    @Override
    public List<Appointment> getByEmployeeId(Long employeeId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE employee_id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, employeeId);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    appointments.add(getMappedAppointment(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting appointments with employee id: {}", employeeId, ex);
        }
        return appointments;
    }

    @Override
    public List<Appointment> getByEmployeeIdAndDate(Long employeeId, LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE client_id = ? AND date = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, employeeId);
            statement.setDate(2, Date.valueOf(date));

            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    appointments.add(getMappedAppointment(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting appointments with client id and date: {} {}", employeeId, date, ex);
        }
        return appointments;
    }

    @Override
    public Appointment updateStatus(Long id, String newStatus) {
        String sql = "UPDATE Appointments SET status = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newStatus);
            statement.setLong(2, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalStateException("Updating status failed, no appointment found with ID: " + id);
            }

            return getById(id);

        } catch (SQLException ex) {
            logger.error("Error updating status for appointment with ID: {}", id, ex);
        }
        return null;
    }

    @Override
    public Appointment getById(Long id) {
        String sql = "SELECT * FROM Appointments WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedAppointment(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting appointment with id: {}", id, ex);
        }
        return null;
    }

    @Override
    public Appointment save(Appointment entity) {
        String sql = "INSERT INTO Appointments (client_id, service_id, employee_id, status, created_at, date, day_of_week," +
                "start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getClientId());
            statement.setLong(2, entity.getServiceId());
            statement.setLong(3, entity.getEmployeeId());
            statement.setString(4, entity.getStatus());
            statement.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()));
            statement.setDate(6, Date.valueOf(entity.getDate()));
            statement.setString(7, entity.getDayOfWeek());
            statement.setTime(8, Time.valueOf(entity.getStartTime()));
            statement.setTime(9, Time.valueOf(entity.getEndTime()));

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving appointment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        }
        catch (SQLException ex) {
            logger.error("Error saving appointment: {}", entity, ex);
        }
        return null;
    }

    @Override
    public Appointment update(Appointment entity) {
        String sql = "UPDATE Appointments SET status = ?, created_at = ?, date = ?, day_of_week = ?, start_time = ?," +
                " end_time = ? WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getStatus());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getCreatedAt()));
            statement.setDate(3, Date.valueOf(entity.getDate()));
            statement.setString(4, entity.getDayOfWeek());
            statement.setTime(5, Time.valueOf(entity.getStartTime()));
            statement.setTime(6, Time.valueOf(entity.getEndTime()));
            statement.setLong(7, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no appointment found with id: " + entity.getId());
            }

            return entity;

        } catch (SQLException ex) {
            logger.error("Error updating appointment with id {}: {}", entity.getId(), ex);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        String sql = "DELETE FROM Appointments WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing appointment with id: {}", id, ex);
        }
    }

    private Appointment getMappedAppointment(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();

        appointment.setId(resultSet.getLong("id"));
        appointment.setClientId(resultSet.getLong("client_id"));
        appointment.setServiceId(resultSet.getLong("service_id"));
        appointment.setEmployeeId(resultSet.getLong("employee_id"));
        appointment.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        appointment.setDate(resultSet.getDate("date").toLocalDate());
        appointment.setDayOfWeek(resultSet.getString("day_of_week"));
        appointment.setStartTime(resultSet.getTime("start_time").toLocalTime());
        appointment.setEndTime(resultSet.getTime("end_time").toLocalTime());

        return appointment;
    }
}
