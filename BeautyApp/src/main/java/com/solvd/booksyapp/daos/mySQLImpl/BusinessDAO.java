package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IBusinessDAO;
import com.solvd.booksyapp.models.Business;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessDAO implements IBusinessDAO {
    private static final Logger logger = LogManager.getLogger(BusinessDAO.class.getName());

    @Override
    public List<Business> getByName(String name) {
        String sql = "SELECT * FROM Businesses WHERE name = ?";
        List<Business> businesses = new ArrayList<>();

        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    businesses.add(getMappedBusiness(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching businesses by name: {}", name, ex);
        }
        return businesses;
    }

    @Override
    public List<Business> getByCity(String city) {
        String sql = "SELECT * FROM Businesses WHERE city = ?";
        List<Business> businesses = new ArrayList<>();

        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, city);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    businesses.add(getMappedBusiness(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching businesses by city: {}", city, ex);
        }
        return businesses;
    }

    @Override
    public List<Business> getByPostCode(String postcode) {
        String sql = "SELECT * FROM Businesses WHERE postcode = ?";
        List<Business> businesses = new ArrayList<>();

        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, postcode);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    businesses.add(getMappedBusiness(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching businesses by postcode: {}", postcode, ex);
        }
        return businesses;
    }

    @Override
    public int countByCity(String city) {
        String sql = "SELECT COUNT(*) FROM Businesses WHERE city = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, city);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error counting employees by city: {}", city, ex);
        }
        return 0;
    }

    @Override
    public Business getById(Long id) {
        String sql = "SELECT * FROM Businesses WHERE id = ?";

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedBusiness(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting employee with id: {}", id, ex);
        }
        return null;
    }

    @Override
    public Business save(Business entity) {
        String sql = "INSERT INTO Businesses (name, address, city, postcode) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAddress());
            statement.setString(3, entity.getCity());
            statement.setString(4, entity.getPostcode());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Saving business failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        } catch (SQLException ex) {
            logger.error("Error saving business: {}", entity, ex);
        }
        return null;
    }

    @Override
    public Business update(Business entity) {
        String sql = "UPDATE Businesses SET name = ?, address = ?, city = ?, postcode = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAddress());
            statement.setString(3, entity.getCity());
            statement.setString(4, entity.getPostcode());
            statement.setLong(5, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no business found with id: " + entity.getId());
            }

            return entity;

        } catch (SQLException ex) {
            logger.error("Error updating business: {}", entity, ex);
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        String sql = "DELETE FROM Businesses WHERE id = ?";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            logger.error("Error removing business by ID: {}", id, ex);
        }
    }

    private Business getMappedBusiness(ResultSet resultSet) throws SQLException {
        Business business = new Business();

        business.setId(resultSet.getLong("id"));
        business.setName(resultSet.getString("name"));
        business.setAddress(resultSet.getString("address"));
        business.setCity(resultSet.getString("city"));
        business.setPostcode(resultSet.getString("postcode"));

        return business;
    }
}
