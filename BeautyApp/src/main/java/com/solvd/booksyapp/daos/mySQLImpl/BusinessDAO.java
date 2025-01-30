package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IBusinessDAO;
import com.solvd.booksyapp.models.Business;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessDAO implements IBusinessDAO {
    private static final Logger logger = LogManager.getLogger(BusinessDAO.class.getName());
    private static final String GET_BY_NAME = "SELECT * FROM Businesses WHERE name = ?";
    private static final String GET_BY_CITY = "SELECT * FROM Businesses WHERE city = ?";
    private static final String GET_BY_POSTCODE = "SELECT * FROM Businesses WHERE postcode = ?";
    private static final String COUNT_BY_CITY = "SELECT COUNT(*) FROM Businesses WHERE city = ?";
    private static final String GET_BY_ID = "SELECT * FROM Businesses WHERE id = ?";
    private static final String SAVE = "INSERT INTO Businesses (name, address, city, postcode) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Businesses SET name = ?, address = ?, city = ?, postcode = ? WHERE id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Businesses WHERE id = ?";

    @Override
    public Business getByName(String name) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_NAME)) {
            statement.setString(1, name);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedBusiness(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching business by name {} : {}", name, ex);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Business> getByCity(String city) {
        List<Business> businesses = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_CITY)) {
            statement.setString(1, city);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    businesses.add(getMappedBusiness(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching businesses by city {} : {}", city, ex);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return businesses;
    }

    @Override
    public List<Business> getByPostCode(String postcode) {
        List<Business> businesses = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_POSTCODE)) {
            statement.setString(1, postcode);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    businesses.add(getMappedBusiness(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error fetching businesses by postcode {} : {}", postcode, ex);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return businesses;
    }

    @Override
    public int countByCity(String city) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(COUNT_BY_CITY)) {
            statement.setString(1, city);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error counting businesses by city {} : {}", city, ex);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return 0;
    }

    @Override
    public Business getById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedBusiness(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting business with id {} : {}", id, ex);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Business save(Business entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
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
            logger.error("Error saving business {} : {}", entity, ex);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Business update(Business entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
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
            logger.error("Error updating business {} : {}", entity, ex);
        }
        finally {
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
            logger.error("Error removing business by ID {} : {}", id, ex);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
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
