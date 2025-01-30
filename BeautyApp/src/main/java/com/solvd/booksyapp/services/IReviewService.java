package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.Review;

import java.math.BigDecimal;
import java.util.List;

public interface IReviewService {
    Review getById(Long id);
    List<Review> getByClientId(Long clientId);
    List<Review> getByEmployeeId(Long employeeId);
    BigDecimal getAverageRatingByEmployeeId(Long employeeId);
    BigDecimal getAverageRatingByBusinessId(Long businessId);
    Review create(Review review);
    Review update(Review review);
    void deleteById(Long id);
}
