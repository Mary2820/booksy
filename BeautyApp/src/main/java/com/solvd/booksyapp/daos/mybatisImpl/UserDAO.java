package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IUserDAO;
import com.solvd.booksyapp.models.User;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserDAO implements IUserDAO {
    @Override
    public User getUserByFullName(String firstName, String lastName) {
        try(SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IUserDAO userDAO = session.getMapper(IUserDAO.class);
            return userDAO.getUserByFullName(firstName, lastName);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IUserDAO mapper = session.getMapper(IUserDAO.class);
            return mapper.getUserByEmail(email);
        }
    }

    @Override
    public List<User> getByRoleId(Long roleId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IUserDAO mapper = session.getMapper(IUserDAO.class);
            return mapper.getByRoleId(roleId);
        }
    }

    @Override
    public User getById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IUserDAO mapper = session.getMapper(IUserDAO.class);
            return mapper.getById(id);
        }
    }

    @Override
    public void save(User entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IUserDAO mapper = session.getMapper(IUserDAO.class);
            mapper.save(entity);
            session.commit();
        }
    }

    @Override
    public void update(User entity) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IUserDAO mapper = session.getMapper(IUserDAO.class);
            mapper.update(entity);
            session.commit();
        }
    }

    @Override
    public void removeById(Long id) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IUserDAO mapper = session.getMapper(IUserDAO.class);
            mapper.removeById(id);
            session.commit();
        }
    }
}
