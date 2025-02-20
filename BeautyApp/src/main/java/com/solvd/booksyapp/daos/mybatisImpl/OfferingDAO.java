package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IOfferingDAO;
import com.solvd.booksyapp.models.Offering;

import java.util.List;

public class OfferingDAO extends AbstractMyBatisDAO<IOfferingDAO> implements IOfferingDAO {
    @Override
    public Offering getByEmployeeIdAndProcedureId(Long employeeId, Long procedureId) {
        return executeInSession(mapper -> mapper.getByEmployeeIdAndProcedureId(employeeId, procedureId));
    }

    @Override
    public void save(Offering offering) {
        executeInSessionVoid(mapper -> mapper.save(offering));
    }

    @Override
    public void updatePrice(Offering offering) {
        executeInSessionVoid(mapper -> mapper.updatePrice(offering));
    }

    @Override
    public void remove(Long employeeId, Long procedureId) {
        executeInSessionVoid(mapper -> mapper.remove(employeeId, procedureId));
    }

    @Override
    public List<Offering> getByEmployeeId(Long employeeId) {
        return executeInSession(mapper -> mapper.getByEmployeeId(employeeId));
    }

    @Override
    public List<Offering> getByBusinessId(Long businessId) {
        return executeInSession(mapper -> mapper.getByBusinessId(businessId));
    }

    @Override
    public List<Offering> getByProcedureId(Long procedureId) {
        return executeInSession(mapper -> mapper.getByProcedureId(procedureId));
    }

    @Override
    protected Class<IOfferingDAO> getMapperClass() {
        return IOfferingDAO.class;
    }
} 