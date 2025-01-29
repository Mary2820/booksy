package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Payment;

public interface IPaymentDAO extends IDAO<Payment> {
    Payment getByAppointmentId(Long appointmentId);
}
