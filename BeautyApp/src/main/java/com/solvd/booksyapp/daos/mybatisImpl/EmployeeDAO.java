package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IEmployeeDAO;
import com.solvd.booksyapp.models.Employee;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO implements IEmployeeDAO {
    @Override
    public List<Employee> getEmployeesByBusinessId(Long businessId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            return mapper.getEmployeesByBusinessId(businessId);
        }
    }

    @Override
    public Employee getByUserId(Long userId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            return mapper.getByUserId(userId);
        }
    }

    @Override
    public List<Employee> getByRatingAbove(BigDecimal rating) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            return mapper.getByRatingAbove(rating);
        }
    }

    @Override
    public List<Employee> getByRatingRange(BigDecimal minRating, BigDecimal maxRating) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            return mapper.getByRatingRange(minRating, maxRating);
        }
    }

    @Override
    public int countByBusinessId(Long businessId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            return mapper.countByBusinessId(businessId);
        }
    }

    @Override
    public boolean updateRating(Long id, BigDecimal rating) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            boolean result = mapper.updateRating(id, rating);
            session.commit();
            return result;
        }
    }

    @Override
    public Employee getById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            return mapper.getById(id);
        }
    }

    @Override
    public void save(Employee entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            mapper.save(entity);
            session.commit();
        }
    }

    @Override
    public void update(Employee entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            mapper.update(entity);
            session.commit();
        }
    }

    @Override
    public void removeById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IEmployeeDAO mapper = session.getMapper(IEmployeeDAO.class);
            mapper.removeById(id);
            session.commit();
        }
    }
} 