package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface IEmployeeDAO extends IDAO<Employee>{
    List<Employee> getEmployeesByBusinessId(Long businessId);
    List<Employee> getByRatingAbove(BigDecimal rating);
    List<Employee> getByRatingRange(BigDecimal minRating, BigDecimal maxRating);
    int countByBusinessId(Long businessId);
    boolean updateRating(Long id, BigDecimal rating);
}
