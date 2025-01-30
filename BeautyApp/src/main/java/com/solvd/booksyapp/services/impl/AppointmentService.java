package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.IAppointmentDAO;
import com.solvd.booksyapp.daos.mySQLImpl.AppointmentDAO;
import com.solvd.booksyapp.models.Appointment;
import com.solvd.booksyapp.services.IAppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AppointmentService implements IAppointmentService {
    private static final Logger logger = LogManager.getLogger(AppointmentService.class.getName());
    private IAppointmentDAO appointmentDAO = new AppointmentDAO();

    @Override
    public Appointment getById(Long id) {
        logger.info("Getting appointment by ID: {}", id);
        Appointment appointment = appointmentDAO.getById(id);
        if (appointment == null) {
            logger.warn("Appointment not found with ID: {}", id);
        }
        return appointment;
    }

    @Override
    public List<Appointment> getByClientId(Long clientId) {
        logger.info("Getting appointments for client ID: {}", clientId);
        List<Appointment> appointments = appointmentDAO.getByClientId(clientId);
        logger.info("Found {} appointments for client ID: {}", appointments.size(), clientId);
        return appointments;
    }

    @Override
    public List<Appointment> getByEmployeeId(Long employeeId) {
        logger.info("Getting appointments for employee ID: {}", employeeId);
        List<Appointment> appointments = appointmentDAO.getByEmployeeId(employeeId);
        logger.info("Found {} appointments for employee ID: {}", appointments.size(), employeeId);
        return appointments;
    }

    @Override
    public List<Appointment> getByEmployeeIdAndDate(Long employeeId, LocalDate date) {
        logger.info("Getting appointments for employee ID: {} on date: {}", employeeId, date);
        List<Appointment> appointments = appointmentDAO.getByEmployeeIdAndDate(employeeId, date);
        logger.info("Found {} appointments for employee ID: {} on date: {}", 
            appointments.size(), employeeId, date);
        return appointments;
    }

    @Override
    public Appointment create(Appointment appointment) {
        logger.info("Creating new appointment for client ID: {} with employee ID: {}", 
            appointment.getClientId(), appointment.getEmployeeId());

        appointment.setCreatedAt(LocalDateTime.now());

        List<Appointment> existingAppointments = appointmentDAO.getByEmployeeIdAndDate(
            appointment.getEmployeeId(), appointment.getDate());
        
        for (Appointment existing : existingAppointments) {
            if (isTimeConflict(existing, appointment)) {
                logger.error("Time conflict detected with existing appointment ID: {}", existing.getId());
                throw new IllegalArgumentException("Time slot is already booked");
            }
        }

        Appointment savedAppointment = appointmentDAO.save(appointment);
        if (savedAppointment != null) {
            logger.info("Successfully created appointment with ID: {}", savedAppointment.getId());
        } else {
            logger.error("Failed to create appointment");
        }
        return savedAppointment;
    }

    @Override
    public Appointment update(Appointment appointment) {
        logger.info("Updating appointment with ID: {}", appointment.getId());
        
        if (appointmentDAO.getById(appointment.getId()) == null) {
            logger.error("Appointment with ID {} not found for update", appointment.getId());
            throw new IllegalArgumentException("Appointment not found");
        }

        Appointment updatedAppointment = appointmentDAO.update(appointment);
        if (updatedAppointment != null) {
            logger.info("Successfully updated appointment with ID: {}", appointment.getId());
        } else {
            logger.error("Failed to update appointment with ID: {}", appointment.getId());
        }
        return updatedAppointment;
    }

    @Override
    public Appointment updateAppointmentStatus(Long id, String newStatus) {
        logger.info("Updating status to {} for appointment ID: {}", newStatus, id);
        
        if (appointmentDAO.getById(id) == null) {
            logger.error("Appointment with ID {} not found for status update", id);
            throw new IllegalArgumentException("Appointment not found");
        }

        Appointment updatedAppointment = appointmentDAO.updateStatus(id, newStatus);
        if (Objects.equals(updatedAppointment.getStatus(), newStatus)) {
            logger.info("Successfully updated status for appointment ID: {}", id);
        } else {
            logger.error("Failed to update status for appointment ID: {}", id);
        }
        return updatedAppointment;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting appointment with ID: {}", id);
        
        if (appointmentDAO.getById(id) == null) {
            logger.error("Appointment with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Appointment not found");
        }

        appointmentDAO.removeById(id);
        logger.info("Successfully deleted appointment with ID: {}", id);
    }

    private boolean isTimeConflict(Appointment existing, Appointment new_) {
        return !new_.getStartTime().isAfter(existing.getEndTime()) && 
               !new_.getEndTime().isBefore(existing.getStartTime());
    }
} 