package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IReviewDAO;
import com.solvd.booksyapp.models.Review;

import java.math.BigDecimal;
import java.util.List;

public class ReviewDAO extends AbstractMyBatisDAO<IReviewDAO> implements IReviewDAO {
    @Override
    public List<Review> getByClientId(Long clientId) {
        return executeInSession(mapper -> mapper.getByClientId(clientId));
    }

    @Override
    public List<Review> getByEmployeeId(Long employeeId) {
        return executeInSession(mapper -> mapper.getByEmployeeId(employeeId));
    }

    @Override
    public BigDecimal getAverageRatingByEmployeeId(Long employeeId) {
        return executeInSession(mapper -> mapper.getAverageRatingByEmployeeId(employeeId));
    }

    @Override
    public BigDecimal getAverageRatingByBusinessId(Long businessId) {
        return executeInSession(mapper -> mapper.getAverageRatingByBusinessId(businessId));
    }

    @Override
    public Review getById(Long id) {
        return executeInSession(mapper -> mapper.getById(id));
    }

    @Override
    public void save(Review entity) {
        executeInSessionVoid(mapper -> mapper.save(entity));
    }

    @Override
    public void update(Review entity) {
        executeInSessionVoid(mapper -> mapper.update(entity));
    }

    @Override
    public void removeById(Long id) {
        executeInSessionVoid(mapper -> mapper.removeById(id));
    }

    @Override
    protected Class<IReviewDAO> getMapperClass() {
        return IReviewDAO.class;
    }
} 