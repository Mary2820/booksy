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
        return null;
    }

    @Override
    public List<User> getByRoleId(Long roleId) {
        return null;
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }
}
