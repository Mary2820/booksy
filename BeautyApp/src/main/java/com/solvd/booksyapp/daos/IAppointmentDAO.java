package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Appointment;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentDAO extends IDAO<Appointment> {
    List<Appointment> getByClientId(@Param("clientId") Long clientId);
    List<Appointment> getByEmployeeId(@Param("employeeId") Long employeeId);
    List<Appointment> getByEmployeeIdAndDate(@Param("employeeId") Long employeeId,@Param("date") LocalDate date);
    void updateStatus(@Param("id") Long id,@Param("newStatus") String newStatus);
}