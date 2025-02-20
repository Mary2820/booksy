package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IBusinessDAO;
import com.solvd.booksyapp.models.Business;

import java.util.List;

public class BusinessDAO extends AbstractMyBatisDAO<IBusinessDAO> implements IBusinessDAO {
    @Override
    public Business getByName(String name) {
        return executeInSession(mapper -> mapper.getByName(name));
    }

    @Override
    public List<Business> getByCity(String city) {
        return executeInSession(mapper -> mapper.getByCity(city));
    }

    @Override
    public List<Business> getByPostCode(String postcode) {
        return executeInSession(mapper -> mapper.getByPostCode(postcode));
    }

    @Override
    public int countByCity(String city) {
        return executeInSession(mapper -> mapper.countByCity(city));
    }

    @Override
    public Business getById(Long id) {
        return executeInSession(mapper -> mapper.getById(id));
    }

    @Override
    public void save(Business entity) {
        executeInSessionVoid(mapper -> mapper.save(entity));
    }

    @Override
    public void update(Business entity) {
        executeInSessionVoid(mapper -> mapper.update(entity));
    }

    @Override
    public void removeById(Long id) {
        executeInSessionVoid(mapper -> mapper.removeById(id));
    }

    @Override
    protected Class<IBusinessDAO> getMapperClass() {
        return IBusinessDAO.class;
    }
}