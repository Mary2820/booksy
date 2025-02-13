package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.INotificationDAO;
import com.solvd.booksyapp.models.Notification;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class NotificationDAO implements INotificationDAO {
    @Override
    public List<Notification> getByClientId(Long clientId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            INotificationDAO mapper = session.getMapper(INotificationDAO.class);
            return mapper.getByClientId(clientId);
        }
    }

    @Override
    public List<Notification> getByEmployeeId(Long employeeId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            INotificationDAO mapper = session.getMapper(INotificationDAO.class);
            return mapper.getByEmployeeId(employeeId);
        }
    }

    @Override
    public Notification getById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            INotificationDAO mapper = session.getMapper(INotificationDAO.class);
            return mapper.getById(id);
        }
    }

    @Override
    public Notification save(Notification entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            INotificationDAO mapper = session.getMapper(INotificationDAO.class);
            mapper.save(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public Notification update(Notification entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            INotificationDAO mapper = session.getMapper(INotificationDAO.class);
            mapper.update(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public void removeById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            INotificationDAO mapper = session.getMapper(INotificationDAO.class);
            mapper.removeById(id);
            session.commit();
        }
    }
} 