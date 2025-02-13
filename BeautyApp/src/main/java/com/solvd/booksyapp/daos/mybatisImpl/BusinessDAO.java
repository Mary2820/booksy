package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IBusinessDAO;
import com.solvd.booksyapp.models.Business;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BusinessDAO implements IBusinessDAO {
    @Override
    public Business getByName(String name) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IBusinessDAO mapper = session.getMapper(IBusinessDAO.class);
            return mapper.getByName(name);
        }
    }

    @Override
    public List<Business> getByCity(String city) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IBusinessDAO mapper = session.getMapper(IBusinessDAO.class);
            return mapper.getByCity(city);
        }
    }

    @Override
    public List<Business> getByPostCode(String postcode) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IBusinessDAO mapper = session.getMapper(IBusinessDAO.class);
            return mapper.getByPostCode(postcode);
        }
    }

    @Override
    public int countByCity(String city) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IBusinessDAO mapper = session.getMapper(IBusinessDAO.class);
            return mapper.countByCity(city);
        }
    }

    @Override
    public Business getById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IBusinessDAO mapper = session.getMapper(IBusinessDAO.class);
            return mapper.getById(id);
        }
    }

    @Override
    public Business save(Business entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IBusinessDAO mapper = session.getMapper(IBusinessDAO.class);
            mapper.save(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public Business update(Business entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IBusinessDAO mapper = session.getMapper(IBusinessDAO.class);
            mapper.update(entity);
            session.commit();
            return entity;
        }
    }

    @Override
    public void removeById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IBusinessDAO mapper = session.getMapper(IBusinessDAO.class);
            mapper.removeById(id);
            session.commit();
        }
    }
} 