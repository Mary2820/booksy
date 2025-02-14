package com.solvd.booksyapp;

import com.solvd.booksyapp.enums.AppointmentStatus;
import com.solvd.booksyapp.enums.UserRole;
import com.solvd.booksyapp.models.*;
import com.solvd.booksyapp.services.impl.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserService();
        userService.getById(1L);
        userService.getByFullName("Sara", "Doray");
        userService.getByEmail("sDoray@gmail.com");
        userService.getByRoleId(UserRole.CLIENT.getId());

        User user = userService.getById(2L);
        user.setPassword("dflijbcobj");

        userService.update(user);

        EmployeeService employeeService = new EmployeeService();
        employeeService.getById(1L);

        Employee employee = new Employee(2L, "barber", BigDecimal.valueOf(4.66), 1L);
        employee.setId(4L);

        employee.setRating(BigDecimal.valueOf(4.55));
        employeeService.update(employee);
        employeeService.getByBusinessId(1L);
        employeeService.getByRatingAbove(BigDecimal.valueOf(4.00));
        employeeService.getByRatingRange(BigDecimal.valueOf(4.00), BigDecimal.valueOf(4.90));
        employeeService.countByBusinessId(1L);
        employeeService.updateRating(4L, BigDecimal.valueOf(4.88));

        AppointmentService appointmentService = new AppointmentService();
        Appointment appointment = appointmentService.getById(4L);
        appointmentService.getByClientId(2L);
        appointmentService.getByEmployeeId(12L);
        appointmentService.getByEmployeeIdAndDate(12L, LocalDate.of(2025, 02, 05));

        appointment.setDate(LocalDate.of(2025, 02, 10));
        appointmentService.update(appointment);
        appointmentService.updateAppointmentStatus(appointment.getId(), AppointmentStatus.SCHEDULED.getName());

        BusinessService businessService = new BusinessService();
        businessService.getById(4L);

        NotificationService notificationService = new NotificationService();
        notificationService.getByClientId(20L);

        OfferingService offeringService = new OfferingService();
        offeringService.getByServiceId(1L);
    }
}
