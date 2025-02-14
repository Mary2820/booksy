package com.solvd.booksyapp.utils;

import com.solvd.booksyapp.daos.*;
import com.solvd.booksyapp.daos.mybatisImpl.*;
import com.solvd.booksyapp.enums.DataBaseProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DAOFactory {
    private static final Logger logger = LogManager.getLogger(DAOFactory.class);
    private static final String PROPERTIES_FILE = "database.properties";
    private static final DataBaseProvider IMPLEMENTATION = loadImplementation();

    public static IAppointmentDAO getAppointmentDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new AppointmentDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.AppointmentDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    public static IBusinessDAO getBusinessDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new BusinessDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.BusinessDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    public static IEmployeeDAO getEmployeeDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new EmployeeDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.EmployeeDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    public static INotificationDAO getNotificationDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new NotificationDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.NotificationDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    public static IOfferingDAO getOfferingDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new OfferingDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.OfferingDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    public static IPaymentDAO getPaymentDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new PaymentDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.PaymentDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    public static IProcedureDAO getProcedureDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new ProcedureDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.ProcedureDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    public static IReviewDAO getReviewDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new ReviewDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.ReviewDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    public static IUserDAO getUserDAO() {
        switch (IMPLEMENTATION) {
            case MYBATIS:
                return new UserDAO();
            case JDBS:
                return new com.solvd.booksyapp.daos.mySQLImpl.UserDAO();
            default:
                throw new IllegalArgumentException("Unknown database implementation: " + IMPLEMENTATION);
        }
    }

    private static DataBaseProvider loadImplementation() {
        try (InputStream input = DAOFactory.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Unable to find " + PROPERTIES_FILE);
            }
            Properties properties = new Properties();
            properties.load(input);
            String impl = properties.getProperty("db.implementation");
            if (impl == null) {
                logger.warn("db.implementation not found in properties, using default 'MYBATIS'");
                return DataBaseProvider.MYBATIS;
            }
            return DataBaseProvider.valueOf(impl.toUpperCase());
        } catch (IOException | IllegalArgumentException e) {
            logger.error("Failed to load properties file or invalid value in db.implementation", e);
            throw new RuntimeException("Failed to load database implementation", e);
        }
    }
}
