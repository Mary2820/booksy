package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.Payment;

public interface IPaymentService {
    Payment getById(Long id);
    Payment getByAppointmentId(Long appointmentId);
    Payment create(Payment payment);
    Payment update(Payment payment);
    void deleteById(Long id);
}
