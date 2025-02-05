package com.solvd.booksyapp;

import com.solvd.booksyapp.enums.AppointmentStatus;
import com.solvd.booksyapp.enums.Category;
import com.solvd.booksyapp.enums.PaymentStatus;
import com.solvd.booksyapp.enums.UserRole;
import com.solvd.booksyapp.models.*;
import com.solvd.booksyapp.services.impl.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        User client = new User("Alina", "Sily", "aSily@gmail.com", "+48852456411",
                "dgffgdg", UserRole.CLIENT.getId());
        User worker = new User("Karina", "Sily", "kSily@gmail.com", "+48852444411",
                "dg451dg", UserRole.EMPLOYEE.getId());

        UserService userService = new UserService();
        client = userService.create(client);
        worker = userService.create(worker);

        userService.getByEmail("aSily@gmail.com");

        userService.getByRoleId(UserRole.EMPLOYEE.getId());
        userService.getByRoleId(UserRole.CLIENT.getId());

        Employee employee = new Employee(5L, "nail master", BigDecimal.valueOf(4.75),  worker.getId());

        EmployeeService employeeService = new EmployeeService();
        employee = employeeService.create(employee);

        employeeService.getByBusinessId(3L);
        employeeService.getByRatingAbove(BigDecimal.valueOf(4.00));

        Procedure procedure = new Procedure(Category.NAILS.getId(), "biology manicure",
                "manicure without cutting tools", 120);

        ProcedureService procedureService = new ProcedureService();
        procedure = procedureService.create(procedure);

        procedureService.getByCategoryId(Category.NAILS.getId());

        Offering offering = new Offering(employee.getId(), procedure.getId(), BigDecimal.valueOf(110.00));

        OfferingService offeringService = new OfferingService();
        offering = offeringService.create(offering);

        offeringService.getByBusinessId(5L);

        Appointment appointment = new Appointment(client.getId(), procedure.getId(), employee.getId(),
                AppointmentStatus.PENDING.getName(), LocalDate.of(2025, 02, 05), "wednesday",
                LocalTime.of(18, 00), LocalTime.of(20, 00));

        AppointmentService appointmentService = new AppointmentService();
        appointment = appointmentService.create(appointment);

        appointment = appointmentService.updateAppointmentStatus(appointment.getId(), AppointmentStatus.SCHEDULED.getName());

        Payment payment = new Payment(appointment.getId(), offering.getPrice(), LocalDate.now(),
                PaymentStatus.IN_PROGRESS.getName());

        PaymentService paymentService = new PaymentService();
        payment = paymentService.create(payment);
        payment.setStatus(PaymentStatus.COMPLETED.getName());
        paymentService.update(payment);

        paymentService.getByAppointmentId(appointment.getId());

    }
}
