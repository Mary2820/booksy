package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface IEmployeeDAO extends IDAO<Employee>{
    List<Employee> getEmployeesByBusinessId(@Param("businessId") Long businessId);
    Employee getByUserId(@Param("userId") Long userId);
    List<Employee> getByRatingAbove(@Param("rating") BigDecimal rating);
    List<Employee> getByRatingRange(@Param("minRating") BigDecimal minRating, @Param("maxRating") BigDecimal maxRating);
    int countByBusinessId(@Param("businessId") Long businessId);
    boolean updateRating(@Param("id") Long id, @Param("rating") BigDecimal rating);
}
