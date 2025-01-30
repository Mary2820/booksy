package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Offering;

import java.util.List;

public interface IOfferingDAO{
    Offering getByEmployeeIdAndProcedureId(Long employeeId, Long procedureId);
    Offering save(Offering offering);
    Offering updatePrice(Offering offering);
    void remove(Long employeeId, Long procedureId);
    List<Offering> getByEmployeeId(Long employeeId);
    List<Offering> getByBusinessId(Long businessId);
    List<Offering> getByProcedureId(Long procedureId);
}
