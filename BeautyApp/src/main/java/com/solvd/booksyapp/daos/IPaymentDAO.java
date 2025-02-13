package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Payment;
import org.apache.ibatis.annotations.Param;

public interface IPaymentDAO extends IDAO<Payment> {
    Payment getByAppointmentId(@Param("appointmentId") Long appointmentId);
}
