package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Offering;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOfferingDAO{
    Offering getByEmployeeIdAndProcedureId(@Param("employeeId") Long employeeId, @Param("procedureId") Long procedureId);
    void save(@Param("offering") Offering offering);
    void updatePrice(@Param("offering") Offering offering);
    void remove(@Param("employeeId") Long employeeId, @Param("procedureId") Long procedureId);
    List<Offering> getByEmployeeId(@Param("employeeId") Long employeeId);
    List<Offering> getByBusinessId(@Param("businessId") Long businessId);
    List<Offering> getByProcedureId(@Param("procedureId") Long procedureId);
}
