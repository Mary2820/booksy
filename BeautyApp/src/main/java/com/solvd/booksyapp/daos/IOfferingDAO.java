package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Offering;

import java.util.List;

public interface IOfferingDAO extends IDAO<Offering>{
    List<Offering> getByEmployeeId(Long employeeId);
    List<Offering> getByBusinessId(Long businessId);
    List<Offering> getByServiceId(Long serviceId);
}
