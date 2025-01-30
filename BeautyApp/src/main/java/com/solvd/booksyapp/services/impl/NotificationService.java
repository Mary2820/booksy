package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.INotificationDAO;
import com.solvd.booksyapp.daos.mySQLImpl.NotificationDAO;
import com.solvd.booksyapp.models.Notification;
import com.solvd.booksyapp.services.INotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationService implements INotificationService {
    private static final Logger logger = LogManager.getLogger(NotificationService.class.getName());
    private INotificationDAO notificationDAO = new NotificationDAO();

    @Override
    public Notification getById(Long id) {
        logger.info("Getting notification by ID: {}", id);
        Notification notification = notificationDAO.getById(id);
        if (notification == null) {
            logger.warn("Notification not found with ID: {}", id);
        }
        return notification;
    }

    @Override
    public List<Notification> getByClientId(Long clientId) {
        logger.info("Getting notifications for client ID: {}", clientId);
        List<Notification> notifications = notificationDAO.getByClientId(clientId);
        if (notifications.isEmpty()) {
            logger.warn("No notifications found for client ID: {}", clientId);
        }
        return notifications;
    }

    @Override
    public List<Notification> getByEmployeeId(Long employeeId) {
        logger.info("Getting notifications for employee ID: {}", employeeId);
        List<Notification> notifications = notificationDAO.getByEmployeeId(employeeId);
        if (notifications.isEmpty()) {
            logger.warn("No notifications found for employee ID: {}", employeeId);
        }
        return notifications;
    }

    @Override
    public Notification create(Notification notification) {
        logger.info("Creating new notification");

        notification.setCreatedAt(LocalDateTime.now());

        Notification createdNotification = notificationDAO.save(notification);
        if (createdNotification != null) {
            logger.info("Successfully created notification with ID: {}", createdNotification.getId());
        } else {
            logger.error("Failed to create notification");
        }
        return createdNotification;
    }

    @Override
    public Notification update(Notification notification) {
        logger.info("Updating notification with ID: {}", notification.getId());

        if (notificationDAO.getById(notification.getId()) == null) {
            logger.error("Notification with ID {} not found for update", notification.getId());
            throw new IllegalArgumentException("Notification not found");
        }

        Notification updatedNotification = notificationDAO.update(notification);
        if (updatedNotification != null) {
            logger.info("Successfully updated notification with ID: {}", notification.getId());
        } else {
            logger.error("Failed to update notification with ID: {}", notification.getId());
        }
        return updatedNotification;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting notification with ID: {}", id);

        if (notificationDAO.getById(id) == null) {
            logger.error("Notification with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Notification not found");
        }

        notificationDAO.removeById(id);
        logger.info("Successfully deleted notification with ID: {}", id);
    }
}