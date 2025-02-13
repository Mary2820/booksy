package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IProcedureDAO;
import com.solvd.booksyapp.models.Procedure;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ProcedureDAO implements IProcedureDAO {
    @Override
    public List<Procedure> getByCategoryId(Long categoryId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IProcedureDAO mapper = session.getMapper(IProcedureDAO.class);
            return mapper.getByCategoryId(categoryId);
        }
    }

    @Override
    public Procedure getByName(String name) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IProcedureDAO mapper = session.getMapper(IProcedureDAO.class);
            return mapper.getByName(name);
        }
    }

    @Override
    public Procedure getById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IProcedureDAO mapper = session.getMapper(IProcedureDAO.class);
            return mapper.getById(id);
        }
    }

    @Override
    public void save(Procedure entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IProcedureDAO mapper = session.getMapper(IProcedureDAO.class);
            mapper.save(entity);
            session.commit();
        }
    }

    @Override
    public void update(Procedure entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IProcedureDAO mapper = session.getMapper(IProcedureDAO.class);
            mapper.update(entity);
            session.commit();
        }
    }

    @Override
    public void removeById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IProcedureDAO mapper = session.getMapper(IProcedureDAO.class);
            mapper.removeById(id);
            session.commit();
        }
    }
} 