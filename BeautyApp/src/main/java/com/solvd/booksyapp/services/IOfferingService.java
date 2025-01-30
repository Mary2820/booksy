package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.Offering;

import java.util.List;

public interface IOfferingService {
    Offering getByEmployeeIdAndServiceId(Long employeeId, Long serviceId);
    Offering create(Offering offering);
    Offering updatePrice(Offering offering);
    void deleteByEmployeeIdAndServiceId(Long employeeId, Long serviceId);
    List<Offering> getByEmployeeId(Long employeeId);
    List<Offering> getByBusinessId(Long businessId);
    List<Offering> getByServiceId(Long serviceId);
}
