package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.INotificationDAO;
import com.solvd.booksyapp.models.Notification;

import java.util.List;

public class NotificationDAO extends AbstractMyBatisDAO<INotificationDAO> implements INotificationDAO {
    @Override
    public List<Notification> getByClientId(Long clientId) {
        return executeInSession(mapper -> mapper.getByClientId(clientId));
    }

    @Override
    public List<Notification> getByEmployeeId(Long employeeId) {
        return executeInSession(mapper -> mapper.getByEmployeeId(employeeId));
    }

    @Override
    public Notification getById(Long id) {
        return executeInSession(mapper -> mapper.getById(id));
    }

    @Override
    public void save(Notification entity) {
        executeInSessionVoid(mapper -> mapper.save(entity));
    }

    @Override
    public void update(Notification entity) {
        executeInSessionVoid(mapper -> mapper.update(entity));
    }

    @Override
    public void removeById(Long id) {
        executeInSessionVoid(mapper -> mapper.removeById(id));
    }

    @Override
    protected Class<INotificationDAO> getMapperClass() {
        return INotificationDAO.class;
    }
}