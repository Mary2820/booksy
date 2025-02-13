package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.IEmployeeDAO;
import com.solvd.booksyapp.models.Employee;
import com.solvd.booksyapp.services.IEmployeeService;
import com.solvd.booksyapp.utils.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeService implements IEmployeeService {
    private static final Logger logger = LogManager.getLogger(EmployeeService.class.getName());
    private IEmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = DAOFactory.getEmployeeDAO();
    }

    @Override
    public Employee getById(Long id) {
        logger.info("Getting employee by ID: {}", id);
        Employee employee = employeeDAO.getById(id);
        if (employee == null) {
            logger.warn("Employee not found with ID: {}", id);
        }
        return employee;
    }

    @Override
    public Employee create(Employee employee) {
        logger.info("Creating new employee");

        if (employeeDAO.getByUserId(employee.getUserId()) != null) {
            logger.error("Employee with user id {} already exists", employee.getUserId());
            throw new IllegalArgumentException("Employee with this user id already exists");
        }

        employeeDAO.save(employee);
        Employee savedEmployee = employeeDAO.getById(employee.getId());

        if (savedEmployee != null) {
            logger.info("Successfully created employee with ID: {}", savedEmployee.getId());
        } else {
            logger.error("Failed to create employee");
        }
        return savedEmployee;
    }

    @Override
    public Employee update(Employee employee) {
        logger.info("Updating employee with ID: {}", employee.getId());

        if (employeeDAO.getById(employee.getId()) == null) {
            logger.error("Employee with ID {} not found for update", employee.getId());
            throw new IllegalArgumentException("Employee not found");
        }

        employeeDAO.update(employee);
        Employee updatedEmployee = employeeDAO.getById(employee.getId());

        if (updatedEmployee != null) {
            logger.info("Successfully updated employee with ID: {}", employee.getId());
        } else {
            logger.error("Failed to update employee with ID: {}", employee.getId());
        }
        return updatedEmployee;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting employee with ID: {}", id);

        if (employeeDAO.getById(id) == null) {
            logger.error("Employee with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Employee not found");
        }

        employeeDAO.removeById(id);
        logger.info("Successfully deleted employee with ID: {}", id);
    }

    @Override
    public List<Employee> getByBusinessId(Long businessId) {
        logger.info("Getting employees for business ID: {}", businessId);
        List<Employee> employees = employeeDAO.getEmployeesByBusinessId(businessId);
        logger.info("Found {} employees for business ID: {}", employees.size(), businessId);
        return employees;
    }

    @Override
    public List<Employee> getByRatingAbove(BigDecimal rating) {
        logger.info("Getting employees with rating above: {}", rating);
        List<Employee> employees = employeeDAO.getByRatingAbove(rating);
        logger.info("Found {} employees with rating above {}", employees.size(), rating);
        return employees;
    }

    @Override
    public List<Employee> getByRatingRange(BigDecimal minRating, BigDecimal maxRating) {
        logger.info("Getting employees with rating between {} and {}", minRating, maxRating);
        List<Employee> employees = employeeDAO.getByRatingRange(minRating, maxRating);
        logger.info("Found {} employees within rating range", employees.size());
        return employees;
    }

    @Override
    public int countByBusinessId(Long businessId) {
        logger.info("Counting employees for business ID: {}", businessId);
        int count = employeeDAO.countByBusinessId(businessId);
        logger.info("Found {} employees for business ID: {}", count, businessId);
        return count;
    }

    @Override
    public boolean updateRating(Long id, BigDecimal rating) {
        logger.info("Updating rating for employee ID: {} to {}", id, rating);

        if (employeeDAO.getById(id) == null) {
            logger.error("Employee with ID {} not found", id);
            throw new IllegalArgumentException("Employee not found");
        }

        boolean updated = employeeDAO.updateRating(id, rating);
        if (updated) {
            logger.info("Successfully updated rating for employee ID: {}", id);
        } else {
            logger.error("Failed to update rating for employee ID: {}", id);
        }
        return updated;
    }
} 