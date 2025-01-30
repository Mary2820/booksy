package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.Appointment;
import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {
    Appointment getById(Long id);
    List<Appointment> getByClientId(Long clientId);
    List<Appointment> getByEmployeeId(Long employeeId);
    List<Appointment> getByEmployeeIdAndDate(Long employeeId, LocalDate date);
    Appointment create(Appointment appointment);
    Appointment update(Appointment appointment);
    Appointment updateAppointmentStatus(Long id, String newStatus);
    void deleteById(Long id);
}
