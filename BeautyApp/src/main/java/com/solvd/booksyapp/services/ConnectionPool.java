package com.solvd.booksyapp.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final int MAX_SIZE = 10;
    private static final ConnectionPool instance = new ConnectionPool();
    private final HikariDataSource dataSource;

    private ConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/testdb");
        config.setUsername("username");
        config.setPassword("password");
        config.setMaximumPoolSize(MAX_SIZE);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        config.setDriverClassName("org.postgresql.Driver");

        this.dataSource = new HikariDataSource(config);
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get connection from pool", e);
        }
    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to release connection", e);
        }
    }

    public int getAvailableConnections() {
        return dataSource.getHikariPoolMXBean().getIdleConnections();
    }

    public void shutdown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
