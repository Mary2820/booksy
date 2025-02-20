package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IUserDAO;
import com.solvd.booksyapp.models.User;

import java.util.List;

public class UserDAO extends AbstractMyBatisDAO<IUserDAO> implements IUserDAO {
    @Override
    public User getUserByFullName(String firstName, String lastName) {
        return executeInSession(mapper -> mapper.getUserByFullName(firstName, lastName));
    }

    @Override
    public User getUserByEmail(String email) {
        return executeInSession(mapper -> mapper.getUserByEmail(email));
    }

    @Override
    public List<User> getByRoleId(Long roleId) {
        return executeInSession(mapper -> mapper.getByRoleId(roleId));
    }

    @Override
    public User getById(Long id) {
        return executeInSession(mapper -> mapper.getById(id));
    }

    @Override
    public void save(User entity) {
        executeInSessionVoid(mapper -> mapper.save(entity));
    }

    @Override
    public void update(User entity) {
        executeInSessionVoid(mapper -> mapper.update(entity));
    }

    @Override
    public void removeById(Long id) {
        executeInSessionVoid(mapper -> mapper.removeById(id));
    }

    @Override
    protected Class<IUserDAO> getMapperClass() {
        return IUserDAO.class;
    }
}
