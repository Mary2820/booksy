package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IAppointmentDAO;
import com.solvd.booksyapp.models.Appointment;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDate;
import java.util.List;

public class AppointmentDAO implements IAppointmentDAO {
    @Override
    public List<Appointment> getByClientId(Long clientId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IAppointmentDAO mapper = session.getMapper(IAppointmentDAO.class);
            return mapper.getByClientId(clientId);
        }
    }

    @Override
    public List<Appointment> getByEmployeeId(Long employeeId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IAppointmentDAO mapper = session.getMapper(IAppointmentDAO.class);
            return mapper.getByEmployeeId(employeeId);
        }
    }

    @Override
    public List<Appointment> getByEmployeeIdAndDate(Long employeeId, LocalDate date) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IAppointmentDAO mapper = session.getMapper(IAppointmentDAO.class);
            return mapper.getByEmployeeIdAndDate(employeeId, date);
        }
    }

    @Override
    public Appointment updateStatus(Long id, String status) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IAppointmentDAO mapper = session.getMapper(IAppointmentDAO.class);
            mapper.updateStatus(id, status);
            session.commit();
            return mapper.getById(id);
        }
    }

    @Override
    public Appointment getById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IAppointmentDAO mapper = session.getMapper(IAppointmentDAO.class);
            return mapper.getById(id);
        }
    }

    @Override
    public Appointment save(Appointment entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IAppointmentDAO mapper = session.getMapper(IAppointmentDAO.class);
            mapper.save(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public Appointment update(Appointment entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IAppointmentDAO mapper = session.getMapper(IAppointmentDAO.class);
            mapper.update(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public void removeById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IAppointmentDAO mapper = session.getMapper(IAppointmentDAO.class);
            mapper.removeById(id);
            session.commit();
        }
    }
}
