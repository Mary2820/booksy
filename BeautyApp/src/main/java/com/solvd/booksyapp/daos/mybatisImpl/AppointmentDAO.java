package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IAppointmentDAO;
import com.solvd.booksyapp.models.Appointment;

import java.time.LocalDate;
import java.util.List;

public class AppointmentDAO extends AbstractMyBatisDAO<IAppointmentDAO> implements IAppointmentDAO {
    @Override
    public List<Appointment> getByClientId(Long clientId) {
        return executeInSession(mapper -> mapper.getByClientId(clientId));
    }

    @Override
    public List<Appointment> getByEmployeeId(Long employeeId) {
        return executeInSession(mapper -> mapper.getByEmployeeId(employeeId));
    }

    @Override
    public List<Appointment> getByEmployeeIdAndDate(Long employeeId, LocalDate date) {
        return executeInSession(mapper -> mapper.getByEmployeeIdAndDate(employeeId, date));
    }

    @Override
    public void updateStatus(Long id, String status) {
        executeInSessionVoid(mapper -> mapper.updateStatus(id, status));
    }

    @Override
    public Appointment getById(Long id) {
        return executeInSession(mapper -> mapper.getById(id));
    }

    @Override
    public void save(Appointment entity) {
        executeInSessionVoid(mapper -> mapper.save(entity));
    }

    @Override
    public void update(Appointment entity) {
        executeInSessionVoid(mapper -> mapper.update(entity));
    }

    @Override
    public void removeById(Long id) {
        executeInSessionVoid(mapper ->mapper.removeById(id));
    }

    @Override
    protected Class<IAppointmentDAO> getMapperClass() {
        return IAppointmentDAO.class;
    }
}
