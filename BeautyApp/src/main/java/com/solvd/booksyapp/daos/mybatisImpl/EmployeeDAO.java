package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IEmployeeDAO;
import com.solvd.booksyapp.models.Employee;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO extends AbstractMyBatisDAO<IEmployeeDAO> implements IEmployeeDAO {
    @Override
    public List<Employee> getEmployeesByBusinessId(Long businessId) {
        return executeInSession(mapper -> mapper.getEmployeesByBusinessId(businessId));
    }

    @Override
    public Employee getByUserId(Long userId) {
        return executeInSession(mapper -> mapper.getByUserId(userId));
    }

    @Override
    public List<Employee> getByRatingAbove(BigDecimal rating) {
        return executeInSession(mapper -> mapper.getByRatingAbove(rating));
    }

    @Override
    public List<Employee> getByRatingRange(BigDecimal minRating, BigDecimal maxRating) {
        return executeInSession(mapper -> mapper.getByRatingRange(minRating, maxRating));
    }

    @Override
    public int countByBusinessId(Long businessId) {
        return executeInSession(mapper -> mapper.countByBusinessId(businessId));
    }

    @Override
    public boolean updateRating(Long id, BigDecimal rating) {
        return executeInSession(mapper -> mapper.updateRating(id, rating));
    }

    @Override
    public Employee getById(Long id) {
        return executeInSession(mapper -> mapper.getById(id));
    }

    @Override
    public void save(Employee entity) {
        executeInSessionVoid(mapper -> mapper.save(entity));
    }

    @Override
    public void update(Employee entity) {
        executeInSessionVoid(mapper -> mapper.update(entity));
    }

    @Override
    public void removeById(Long id) {
        executeInSessionVoid(mapper -> mapper.removeById(id));
    }

    @Override
    protected Class<IEmployeeDAO> getMapperClass() {
        return IEmployeeDAO.class;
    }
} 