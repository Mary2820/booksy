package com.solvd.booksyapp;

import com.solvd.booksyapp.enums.AppointmentStatus;
import com.solvd.booksyapp.enums.PaymentStatus;
import com.solvd.booksyapp.models.Appointment;
import com.solvd.booksyapp.models.Payment;
import com.solvd.booksyapp.services.impl.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Appointment appointment = new Appointment(7L, 6L,  7L, AppointmentStatus.PENDING.getName(),
                LocalDate.of(2025, 02, 02), "sunday", LocalTime.of(14, 00),
                LocalTime.of(15, 00));

        PaymentService paymentService = new PaymentService();
        Payment payment = new Payment(5L, BigDecimal.valueOf(100.25),
                LocalDate.of(2025, 01, 31), PaymentStatus.IN_PROGRESS.getName());
        payment.setId(2L);
        paymentService.update(payment);
    }
}
