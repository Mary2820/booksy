package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.IOfferingDAO;
import com.solvd.booksyapp.daos.mySQLImpl.OfferingDAO;
import com.solvd.booksyapp.models.Offering;
import com.solvd.booksyapp.services.IOfferingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OfferingService implements IOfferingService {
    private static final Logger logger = LogManager.getLogger(OfferingService.class.getName());
    private IOfferingDAO offeringDAO = new OfferingDAO();

    @Override
    public Offering getByEmployeeIdAndServiceId(Long employeeId, Long serviceId) {
        logger.info("Getting offering for employee ID: {} and service ID: {}", employeeId, serviceId);
        Offering offering = offeringDAO.getByEmployeeIdAndProcedureId(employeeId, serviceId);
        if (offering == null) {
            logger.warn("Offering not found for employee ID: {} and service ID: {}", employeeId, serviceId);
        }
        return offering;
    }

    @Override
    public Offering create(Offering offering) {
        logger.info("Creating new offering");
        
        if (offeringDAO.getByEmployeeIdAndProcedureId(offering.getEmployeeId(), offering.getProcedureId()) != null) {
            logger.error("Offering already exists for employee ID: {} and service ID: {}", 
                offering.getEmployeeId(), offering.getProcedureId());
            throw new IllegalArgumentException("Offering already exists for this employee and service");
        }

        Offering savedOffering = offeringDAO.save(offering);
        if (savedOffering != null) {
            logger.info("Successfully created offering for employee ID: {} and service ID: {}", 
                offering.getEmployeeId(), offering.getProcedureId());
        } else {
            logger.error("Failed to create offering");
        }
        return savedOffering;
    }

    @Override
    public Offering updatePrice(Offering offering) {
        logger.info("Updating price for offering with employee ID: {} and service ID: {}", 
            offering.getEmployeeId(), offering.getProcedureId());

        if (offeringDAO.getByEmployeeIdAndProcedureId(offering.getEmployeeId(), offering.getProcedureId()) == null) {
            logger.error("Offering not found for update");
            throw new IllegalArgumentException("Offering not found");
        }

        Offering updatedOffering = offeringDAO.updatePrice(offering);
        if (updatedOffering != null) {
            logger.info("Successfully updated offering price");
        } else {
            logger.error("Failed to update offering price");
        }
        return updatedOffering;
    }

    @Override
    public void deleteByEmployeeIdAndServiceId(Long employeeId, Long serviceId) {
        logger.info("Deleting offering for employee ID: {} and service ID: {}", employeeId, serviceId);

        if (offeringDAO.getByEmployeeIdAndProcedureId(employeeId, serviceId) == null) {
            logger.error("Offering not found for deletion");
            throw new IllegalArgumentException("Offering not found");
        }

        offeringDAO.remove(employeeId, serviceId);
        logger.info("Successfully deleted offering");
    }

    @Override
    public List<Offering> getByEmployeeId(Long employeeId) {
        logger.info("Getting offerings for employee ID: {}", employeeId);
        List<Offering> offerings = offeringDAO.getByEmployeeId(employeeId);
        logger.info("Found {} offerings for employee ID: {}", offerings.size(), employeeId);
        return offerings;
    }

    @Override
    public List<Offering> getByBusinessId(Long businessId) {
        logger.info("Getting offerings for business ID: {}", businessId);
        List<Offering> offerings = offeringDAO.getByBusinessId(businessId);
        logger.info("Found {} offerings for business ID: {}", offerings.size(), businessId);
        return offerings;
    }

    @Override
    public List<Offering> getByServiceId(Long serviceId) {
        logger.info("Getting offerings for service ID: {}", serviceId);
        List<Offering> offerings = offeringDAO.getByProcedureId(serviceId);
        logger.info("Found {} offerings for service ID: {}", offerings.size(), serviceId);
        return offerings;
    }
}
