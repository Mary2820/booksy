package com.solvd.booksyapp.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class.getName());
    private final BlockingQueue<Connection> connections;
    private static final int MAX_SIZE = 10;
    private static ConnectionPool instance;
    private static final String PATH_TO_PROPERTIES = "src/main/resources/database.properties";
    private static final Properties properties = new Properties();

    private ConnectionPool() {
        this.connections = new ArrayBlockingQueue<>(MAX_SIZE);
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            instance.loadProperties();
            instance.initializeConnections();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return connections.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Connection waiting interrupted", e);
        }
    }

    public void releaseConnection(Connection connection) {
        connections.add(connection);
    }

    public int getAvailableConnections() {
        return connections.size();
    }

    private void initializeConnections() {
        for (int i = 0; i < MAX_SIZE; i++) {
            try {
                connections.add(DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password")));
            } catch (SQLException ex) {
                logger.error("Failed to establish connection: ", ex);
            }
        }
    }

    private void loadProperties() {
        try (FileInputStream input = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(input);
        } catch (IOException e) {
            logger.error("Failed to load database properties file", e);
        }
    }
}
