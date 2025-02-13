package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IPaymentDAO;
import com.solvd.booksyapp.models.Payment;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class PaymentDAO implements IPaymentDAO {
    @Override
    public Payment getByAppointmentId(Long appointmentId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IPaymentDAO mapper = session.getMapper(IPaymentDAO.class);
            return mapper.getByAppointmentId(appointmentId);
        }
    }

    @Override
    public Payment getById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IPaymentDAO mapper = session.getMapper(IPaymentDAO.class);
            return mapper.getById(id);
        }
    }

    @Override
    public Payment save(Payment entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IPaymentDAO mapper = session.getMapper(IPaymentDAO.class);
            mapper.save(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public Payment update(Payment entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IPaymentDAO mapper = session.getMapper(IPaymentDAO.class);
            mapper.update(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public void removeById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IPaymentDAO mapper = session.getMapper(IPaymentDAO.class);
            mapper.removeById(id);
            session.commit();
        }
    }
} 