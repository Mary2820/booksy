package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IPaymentDAO;
import com.solvd.booksyapp.models.Payment;

public class PaymentDAO extends AbstractMyBatisDAO<IPaymentDAO> implements IPaymentDAO {
    @Override
    public Payment getByAppointmentId(Long appointmentId) {
        return executeInSession(mapper -> mapper.getByAppointmentId(appointmentId));
    }

    @Override
    public Payment getById(Long id) {
        return executeInSession(mapper -> mapper.getById(id));
    }

    @Override
    public void save(Payment entity) {
        executeInSessionVoid(mapper -> mapper.save(entity));
    }

    @Override
    public void update(Payment entity) {
        executeInSessionVoid(mapper -> mapper.update(entity));
    }

    @Override
    public void removeById(Long id) {
        executeInSessionVoid(mapper -> mapper.removeById(id));
    }

    @Override
    protected Class<IPaymentDAO> getMapperClass() {
        return IPaymentDAO.class;
    }
} 