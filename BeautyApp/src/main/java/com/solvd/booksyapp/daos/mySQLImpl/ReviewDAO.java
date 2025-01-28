package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IReviewDAO;
import com.solvd.booksyapp.models.Review;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO implements IReviewDAO {
    private static final Logger logger = LogManager.getLogger(ReviewDAO.class.getName());

    @Override
    public List<Review> getByClientId(Long clientId) {
        String sql = " SELECT * FROM Reviews r" +
                " LEFT JOIN Appointments a" +
                " ON r.appointment_id = a.id" +
                " WHERE a.client_id = ?";
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, clientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(getMappedReview(resultSet));
                }
            }

        } catch (SQLException ex) {
            logger.error("Error fetching reviews for client with ID {} : {}", clientId, ex);
        }

        return reviews;
    }

    @Override
    public List<Review> getByEmployeeId(Long employeeId) {
        String sql = " SELECT * FROM Reviews r" +
                " LEFT JOIN Appointments a" +
                " ON r.appointment_id = a.id" +
                " WHERE a.employee_id = ?";
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(getMappedReview(resultSet));
                }
            }

        } catch (SQLException ex) {
            logger.error("Error fetching reviews for employee with ID {} : {}", employeeId, ex);
        }

        return reviews;
    }

    @Override
    public BigDecimal getAverageRatingByEmployeeId(Long employeeId) {
        String sql = " SELECT AVG(r.rating) AS average_rating FROM Reviews r" +
        " LEFT JOIN Appointments a ON r.appointment_id = a.id" +
        " WHERE a.employee_id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BigDecimal averageRating = resultSet.getBigDecimal("average_rating");
                    return averageRating != null ? averageRating : BigDecimal.ZERO;
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching average rating for employee with ID {} : {}", employeeId, ex);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getAverageRatingByBusinessId(Long businessId) {
        String sql = "SELECT AVG(r.rating) AS average_rating FROM Reviews r" +
        " LEFT JOIN Appointments a ON r.appointment_id = a.id" +
        " LEFT JOIN Clients c ON a.client_id = c.id" +
        " WHERE c.business_id = ? ";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, businessId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BigDecimal averageRating = resultSet.getBigDecimal("average_rating");
                    return averageRating != null ? averageRating : BigDecimal.ZERO;
                }
            }

        } catch (SQLException ex) {
            logger.error("Error fetching average rating for business with ID {} : {}", businessId, ex);
        }

        return BigDecimal.ZERO;
    }

    @Override
    public Review getById(Long id) {
        String sql = "SELECT * FROM Reviews WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedReview(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting review with id {} : {}", id, ex);
        }
        return null;
    }

    @Override
    public Review save(Review entity) {
        String sql = "INSERT INTO Reviews (appointment_id, rating, comment, created_at) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getAppointmentId());
            statement.setBigDecimal(2, entity.getRating());
            statement.setString(3, entity.getComment());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getCreatedAt()));


            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving review failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        }
        catch (SQLException ex) {
            logger.error("Error saving review {} : {}", entity, ex);
        }
        return null;
    }

    @Override
    public Review update(Review entity) {
        String sql = "UPDATE Reviews SET rating = ?, comment = ? WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, entity.getRating());
            statement.setString(2, entity.getComment());
            statement.setLong(3, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no review found with id: " + entity.getId());
            }

            return entity;

        } catch (SQLException ex) {
            logger.error("Error updating review with id {}: {}", entity.getId(), ex);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        String sql = "DELETE FROM Reviews WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing review with id {} : {}", id, ex);
        }
    }

    private Review getMappedReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();

        review.setId(resultSet.getLong("id"));
        review.setAppointmentId(resultSet.getLong("appointment_id"));
        review.setRating(resultSet.getBigDecimal("rating"));
        review.setComment(resultSet.getString("comment"));
        review.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

        return review;
    }
}
