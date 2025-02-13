package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Review;

import java.math.BigDecimal;
import java.util.List;

public interface IReviewDAO extends IDAO<Review> {
    List<Review> getByClientId(@Param("clientId") Long clientId);
    List<Review> getByEmployeeId(@Param("employeeId") Long employeeId);
    BigDecimal getAverageRatingByEmployeeId(@Param("employeeId") Long employeeId);
    BigDecimal getAverageRatingByBusinessId(@Param("businessId") Long businessId);
}
