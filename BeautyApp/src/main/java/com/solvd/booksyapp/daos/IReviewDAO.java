package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Review;

import java.math.BigDecimal;
import java.util.List;

public interface IReviewDAO extends IDAO<Review> {
    List<Review> getByClientId(Long clientId);
    List<Review> getByEmployeeId(Long employeeId);
    BigDecimal getAverageRatingByEmployeeId(Long employeeId);
    BigDecimal getAverageRatingByBusinessId(Long businessId);
}
