package com.solvd.booksyapp.services;

import com.solvd.booksyapp.constants.Credentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class.getName());
    private final BlockingQueue<Connection> connections;
    private static final int MAX_SIZE = 10;
    private static ConnectionPool instance;

    private ConnectionPool() {
        this.connections = new ArrayBlockingQueue<>(MAX_SIZE);
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
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
                connections.add(DriverManager.getConnection(Credentials.DB_URL, Credentials.DB_USER, Credentials.DB_PASSWORD));
            } catch (SQLException ex) {
                logger.error("Failed to establish connection: ", ex);
            }
        }
    }
}
