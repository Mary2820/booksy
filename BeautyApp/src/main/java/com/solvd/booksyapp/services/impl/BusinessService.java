package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.IBusinessDAO;
import com.solvd.booksyapp.models.Business;
import com.solvd.booksyapp.services.IBusinessService;
import com.solvd.booksyapp.utils.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BusinessService implements IBusinessService {
    private static final Logger logger = LogManager.getLogger(BusinessService.class.getName());
    private IBusinessDAO businessDAO;

    public BusinessService() {
        this.businessDAO = DAOFactory.getBusinessDAO();
    }

    @Override
    public Business getById(Long id) {
        logger.info("Getting business by ID: {}", id);
        Business business = businessDAO.getById(id);
        if (business == null) {
            logger.warn("Business not found with ID: {}", id);
        }
        return business;
    }

    @Override
    public Business getByName(String name) {
        logger.info("Getting business by name: {}", name);
        Business business = businessDAO.getByName(name);
        if (business == null) {
            logger.warn("Business not found with name: {}", name);
        }
        return business;
    }

    @Override
    public List<Business> getByCity(String city) {
        logger.info("Getting businesses in city: {}", city);
        List<Business> businesses = businessDAO.getByCity(city);
        logger.info("Found {} businesses in city: {}", businesses.size(), city);
        return businesses;
    }

    @Override
    public List<Business> getByPostCode(String postcode) {
        logger.info("Getting businesses by postcode: {}", postcode);
        List<Business> businesses = businessDAO.getByPostCode(postcode);
        logger.info("Found {} businesses in postcode area: {}", businesses.size(), postcode);
        return businesses;
    }

    @Override
    public int countByCity(String city) {
        logger.info("Counting businesses in city: {}", city);
        int count = businessDAO.countByCity(city);
        logger.info("Found {} businesses in city: {}", count, city);
        return count;
    }

    @Override
    public Business create(Business business) {
        logger.info("Creating new business with name: {}", business.getName());

        if (businessDAO.getByName(business.getName()) != null) {
            logger.error("Business with name {} already exists", business.getName());
            throw new IllegalArgumentException("Business with this name already exists");
        }

        businessDAO.save(business);
        Business savedBusiness = businessDAO.getById(business.getId());
        if (savedBusiness != null) {
            logger.info("Successfully created business with ID: {}", savedBusiness.getId());
        } else {
            logger.error("Failed to create business");
        }
        return savedBusiness;
    }

    @Override
    public Business update(Business business) {
        logger.info("Updating business with ID: {}", business.getId());

        Business existingBusiness = businessDAO.getById(business.getId());
        if (existingBusiness == null) {
            logger.error("Business with ID {} not found for update", business.getId());
            throw new IllegalArgumentException("Business not found");
        }

        if (!existingBusiness.getName().equals(business.getName())) {
            Business businessWithSameName = businessDAO.getByName(business.getName());
            if (businessWithSameName != null) {
                logger.error("Business with name {} already exists", business.getName());
                throw new IllegalArgumentException("Business name already taken");
            }
        }

        businessDAO.update(business);
        Business updatedBusiness = businessDAO.getById(business.getId());

        if (updatedBusiness != null) {
            logger.info("Successfully updated business with ID: {}", business.getId());
        } else {
            logger.error("Failed to update business with ID: {}", business.getId());
        }
        return updatedBusiness;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting business with ID: {}", id);
        
        if (businessDAO.getById(id) == null) {
            logger.error("Business with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Business not found");
        }

        businessDAO.removeById(id);
        logger.info("Successfully deleted business with ID: {}", id);
    }
} 