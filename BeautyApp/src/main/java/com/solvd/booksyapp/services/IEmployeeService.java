package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface IEmployeeService {
    Employee getById(Long id);
    Employee create(Employee employee);
    Employee update(Employee employee);
    void deleteById(Long id);
    List<Employee> getByBusinessId(Long businessId);
    List<Employee> getByRatingAbove(BigDecimal rating);
    List<Employee> getByRatingRange(BigDecimal minRating, BigDecimal maxRating);
    int countByBusinessId(Long businessId);
    boolean updateRating(Long id, BigDecimal rating);
} 