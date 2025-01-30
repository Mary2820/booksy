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
    private static final String GET_BY_CLIENT_ID = 
        "SELECT * FROM Reviews r " +
        "LEFT JOIN Appointments a ON r.appointment_id = a.id " +
        "WHERE a.client_id = ?";
    private static final String GET_BY_EMPLOYEE_ID = 
        "SELECT * FROM Reviews r " +
        "LEFT JOIN Appointments a ON r.appointment_id = a.id " +
        "WHERE a.employee_id = ?";
    private static final String GET_AVG_RATING_BY_EMPLOYEE = 
        "SELECT AVG(r.rating) AS average_rating FROM Reviews r " +
        "LEFT JOIN Appointments a ON r.appointment_id = a.id " +
        "WHERE a.employee_id = ?";
    private static final String GET_AVG_RATING_BY_BUSINESS = 
        "SELECT AVG(r.rating) AS average_rating FROM Reviews r " +
        "LEFT JOIN Appointments a ON r.appointment_id = a.id " +
        "LEFT JOIN Clients c ON a.client_id = c.id " +
        "WHERE c.business_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM Reviews WHERE id = ?";
    private static final String SAVE = "INSERT INTO Reviews (appointment_id, rating, comment, created_at) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Reviews SET rating = ?, comment = ? WHERE id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Reviews WHERE id = ?";

    @Override
    public List<Review> getByClientId(Long clientId) {
        List<Review> reviews = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_CLIENT_ID)) {
            statement.setLong(1, clientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(getMappedReview(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching reviews for client with ID {} : {}", clientId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return reviews;
    }

    @Override
    public List<Review> getByEmployeeId(Long employeeId) {
        List<Review> reviews = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_EMPLOYEE_ID)) {
            statement.setLong(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(getMappedReview(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching reviews for employee with ID {} : {}", employeeId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return reviews;
    }

    @Override
    public BigDecimal getAverageRatingByEmployeeId(Long employeeId) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_AVG_RATING_BY_EMPLOYEE)) {
            statement.setLong(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BigDecimal averageRating = resultSet.getBigDecimal("average_rating");
                    return averageRating != null ? averageRating : BigDecimal.ZERO;
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching average rating for employee with ID {} : {}", employeeId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getAverageRatingByBusinessId(Long businessId) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_AVG_RATING_BY_BUSINESS)) {
            statement.setLong(1, businessId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BigDecimal averageRating = resultSet.getBigDecimal("average_rating");
                    return averageRating != null ? averageRating : BigDecimal.ZERO;
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching average rating for business with ID {} : {}", businessId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public Review getById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedReview(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting review with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Review save(Review entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
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
        } catch (SQLException ex) {
            logger.error("Error saving review {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Review update(Review entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setBigDecimal(1, entity.getRating());
            statement.setString(2, entity.getComment());
            statement.setLong(3, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no review found with id: " + entity.getId());
            }

            return entity;
        } catch (SQLException ex) {
            logger.error("Error updating review {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error removing review by ID {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
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
