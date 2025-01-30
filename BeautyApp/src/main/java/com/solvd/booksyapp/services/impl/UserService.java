package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.IUserDAO;
import com.solvd.booksyapp.daos.mySQLImpl.UserDAO;
import com.solvd.booksyapp.models.User;
import com.solvd.booksyapp.services.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserService implements IUserService {
    private static final Logger logger = LogManager.getLogger(UserService.class.getName());
    private IUserDAO userDAO = new UserDAO();

    @Override
    public User getById(Long id) {
        logger.info("Getting user by ID: {}", id);
        User user = userDAO.getById(id);
        if (user == null) {
            logger.warn("User not found with ID: {}", id);
        }
        return user;
    }

    @Override
    public User getByFullName(String firstName, String lastName) {
        logger.info("Getting user by full name: {} {}", firstName, lastName);
        User user = userDAO.getUserByFullName(firstName, lastName);
        if (user == null) {
            logger.warn("User not found with name: {} {}", firstName, lastName);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        logger.info("Getting user by email: {}", email);
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            logger.warn("User not found with email: {}", email);
        }
        return user;
    }

    @Override
    public List<User> getByRoleId(Long roleId) {
        logger.info("Getting users by role ID: {}", roleId);
        List<User> users = userDAO.getByRoleId(roleId);
        logger.info("Found {} users with role ID: {}", users.size(), roleId);
        return users;
    }

    @Override
    public User create(User user) {
        logger.info("Creating new user with email: {}", user.getEmail());
        
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            logger.error("User with email {} already exists", user.getEmail());
            throw new IllegalArgumentException("User with this email already exists");
        }

        User savedUser = userDAO.save(user);
        if (savedUser != null) {
            logger.info("Successfully created user with ID: {}", savedUser.getId());
        } else {
            logger.error("Failed to create user");
        }
        return savedUser;
    }

    @Override
    public User update(User user) {
        logger.info("Updating user with ID: {}", user.getId());
        
        if (userDAO.getById(user.getId()) == null) {
            logger.error("User with ID {} not found for update", user.getId());
            throw new IllegalArgumentException("User not found");
        }

        User updatedUser = userDAO.update(user);
        if (updatedUser != null) {
            logger.info("Successfully updated user with ID: {}", user.getId());
        } else {
            logger.error("Failed to update user with ID: {}", user.getId());
        }
        return updatedUser;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting user with ID: {}", id);
        
        if (userDAO.getById(id) == null) {
            logger.error("User with ID {} not found for deletion", id);
            throw new IllegalArgumentException("User not found");
        }

        userDAO.removeById(id);
        logger.info("Successfully deleted user with ID: {}", id);
    }
} 