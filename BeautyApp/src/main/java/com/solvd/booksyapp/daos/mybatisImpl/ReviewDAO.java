package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IReviewDAO;
import com.solvd.booksyapp.models.Review;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.List;

public class ReviewDAO implements IReviewDAO {
    @Override
    public List<Review> getByClientId(Long clientId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IReviewDAO mapper = session.getMapper(IReviewDAO.class);
            return mapper.getByClientId(clientId);
        }
    }

    @Override
    public List<Review> getByEmployeeId(Long employeeId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IReviewDAO mapper = session.getMapper(IReviewDAO.class);
            return mapper.getByEmployeeId(employeeId);
        }
    }

    @Override
    public BigDecimal getAverageRatingByEmployeeId(Long employeeId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IReviewDAO mapper = session.getMapper(IReviewDAO.class);
            return mapper.getAverageRatingByEmployeeId(employeeId);
        }
    }

    @Override
    public BigDecimal getAverageRatingByBusinessId(Long businessId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IReviewDAO mapper = session.getMapper(IReviewDAO.class);
            return mapper.getAverageRatingByBusinessId(businessId);
        }
    }

    @Override
    public Review getById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IReviewDAO mapper = session.getMapper(IReviewDAO.class);
            return mapper.getById(id);
        }
    }

    @Override
    public Review save(Review entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IReviewDAO mapper = session.getMapper(IReviewDAO.class);
            mapper.save(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public Review update(Review entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IReviewDAO mapper = session.getMapper(IReviewDAO.class);
            mapper.update(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public void removeById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IReviewDAO mapper = session.getMapper(IReviewDAO.class);
            mapper.removeById(id);
            session.commit();
        }
    }
} 