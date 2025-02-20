package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IProcedureDAO;
import com.solvd.booksyapp.models.Procedure;

import java.util.List;

public class ProcedureDAO extends AbstractMyBatisDAO<IProcedureDAO> implements IProcedureDAO {
    @Override
    public List<Procedure> getByCategoryId(Long categoryId) {
        return executeInSession(mapper -> mapper.getByCategoryId(categoryId));
    }

    @Override
    public Procedure getByName(String name) {
        return executeInSession(mapper -> mapper.getByName(name));
    }

    @Override
    public Procedure getById(Long id) {
        return executeInSession(mapper -> mapper.getById(id));
    }

    @Override
    public void save(Procedure entity) {
        executeInSessionVoid(mapper -> mapper.save(entity));
    }

    @Override
    public void update(Procedure entity) {
        executeInSessionVoid(mapper -> mapper.update(entity));
    }

    @Override
    public void removeById(Long id) {
        executeInSessionVoid(mapper -> mapper.removeById(id));
    }

    @Override
    protected Class<IProcedureDAO> getMapperClass() {
        return IProcedureDAO.class;
    }
} 