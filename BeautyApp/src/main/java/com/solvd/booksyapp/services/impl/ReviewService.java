package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.IReviewDAO;
import com.solvd.booksyapp.daos.mySQLImpl.ReviewDAO;
import com.solvd.booksyapp.models.Review;
import com.solvd.booksyapp.services.IReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewService implements IReviewService {
    private static final Logger logger = LogManager.getLogger(ReviewService.class.getName());
    private IReviewDAO reviewDAO = new ReviewDAO();

    @Override
    public Review getById(Long id) {
        logger.info("Getting review by ID: {}", id);
        Review review = reviewDAO.getById(id);
        if (review == null) {
            logger.warn("Review not found with ID: {}", id);
        }
        return review;
    }

    @Override
    public List<Review> getByClientId(Long clientId) {
        logger.info("Getting reviews by client ID: {}", clientId);
        List<Review> reviews = reviewDAO.getByClientId(clientId);
        logger.info("Found {} reviews with client ID: {}", reviews.size(), clientId);
        return reviews;
    }

    @Override
    public List<Review> getByEmployeeId(Long employeeId) {
        logger.info("Getting reviews by employee ID: {}", employeeId);
        List<Review> reviews = reviewDAO.getByEmployeeId(employeeId);
        logger.info("Found {} reviews with employee ID: {}", reviews.size(), employeeId);
        return reviews;
    }

    @Override
    public BigDecimal getAverageRatingByEmployeeId(Long employeeId) {
        logger.info("Getting average rating for employee ID: {}", employeeId);
        return reviewDAO.getAverageRatingByEmployeeId(employeeId);
    }

    @Override
    public BigDecimal getAverageRatingByBusinessId(Long businessId) {
        logger.info("Getting average rating for business ID: {}", businessId);
        return reviewDAO.getAverageRatingByBusinessId(businessId);
    }

    @Override
    public Review create(Review review) {
        logger.info("Creating new review for appointment ID: {}", review.getAppointmentId());

        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewDAO.save(review);
        if (savedReview != null) {
            logger.info("Successfully created review with ID: {}", savedReview.getId());
        } else {
            logger.error("Failed to create review");
        }
        return savedReview;
    }

    @Override
    public Review update(Review review) {
        logger.info("Updating review with ID: {}", review.getId());
        
        if (reviewDAO.getById(review.getId()) == null) {
            logger.error("Review with ID {} not found for update", review.getId());
            throw new IllegalArgumentException("Review not found");
        }

        Review updatedReview = reviewDAO.update(review);
        if (updatedReview != null) {
            logger.info("Successfully updated review with ID: {}", review.getId());
        } else {
            logger.error("Failed to update review with ID: {}", review.getId());
        }
        return updatedReview;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting review with ID: {}", id);
        
        if (reviewDAO.getById(id) == null) {
            logger.error("Review with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Review not found");
        }

        reviewDAO.removeById(id);
        logger.info("Successfully deleted review with ID: {}", id);
    }
}
