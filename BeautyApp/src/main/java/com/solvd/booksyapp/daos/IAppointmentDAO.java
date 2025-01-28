package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentDAO extends IDAO<Appointment> {
    List<Appointment> getByClientId(Long clientId);
    List<Appointment> getByEmployeeId(Long employeeId);
    List<Appointment> getByEmployeeIdAndDate(Long employeeId, LocalDate date);
    Appointment updateStatus(Long id, String newStatus);
}