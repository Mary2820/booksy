package com.solvd.booksyapp.daos.mySQLImpl;

import com.solvd.booksyapp.daos.IProcedureDAO;
import com.solvd.booksyapp.models.Procedure;
import com.solvd.booksyapp.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedureDAO implements IProcedureDAO {
    private static final Logger logger = LogManager.getLogger(ProcedureDAO.class.getName());
    private static final String GET_BY_CATEGORY_ID = "SELECT * FROM Procedures WHERE category_id = ?";
    private static final String GET_BY_NAME = "SELECT * FROM Procedures WHERE name = ?";
    private static final String GET_BY_ID = "SELECT * FROM Procedures WHERE id = ?";
    private static final String SAVE = "INSERT INTO Procedures (category_id, name, description, duration) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Procedures SET name = ?, description = ?, duration = ? WHERE id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM Procedures WHERE id = ?";

    @Override
    public List<Procedure> getByCategoryId(Long categoryId) {
        List<Procedure> procedures = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_CATEGORY_ID)) {
            statement.setLong(1, categoryId);

            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    procedures.add(getMappedProcedure(resultSet));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting procedures with category_id {} : {}", categoryId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return procedures;
    }

    @Override
    public Procedure getByName(String name) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_NAME)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedProcedure(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting procedure with name {}:  {}", name, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Procedure getById(Long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedProcedure(resultSet);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error getting procedure with id {}: {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Procedure save(Procedure entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getCategoryId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getDuration());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving procedure failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
            return entity;
        } catch (SQLException ex) {
            logger.error("Error saving procedure {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Procedure update(Procedure entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getDuration());
            statement.setLong(4, entity.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalStateException("Update failed, no procedure found with id: " + entity.getId());
            }

            return entity;
        } catch (SQLException ex) {
            logger.error("Error updating procedure with id {}: {}", entity.getId(), ex);
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
            logger.error("Error removing procedure with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private Procedure getMappedProcedure(ResultSet resultSet) throws SQLException {
        Procedure procedure = new Procedure();

        procedure.setId(resultSet.getLong("id"));
        procedure.setCategoryId(resultSet.getLong("category_id"));
        procedure.setName(resultSet.getString("name"));
        procedure.setDescription(resultSet.getString("description"));
        procedure.setDuration(resultSet.getInt("duration"));

        return procedure;
    }
}
