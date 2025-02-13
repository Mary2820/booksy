package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.IProcedureDAO;
import com.solvd.booksyapp.models.Procedure;
import com.solvd.booksyapp.services.IProcedureService;
import com.solvd.booksyapp.utils.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProcedureService implements IProcedureService {
    private static final Logger logger = LogManager.getLogger(ProcedureService.class.getName());
    private IProcedureDAO procedureDAO;

    public ProcedureService() {
        this.procedureDAO = DAOFactory.getProcedureDAO();
    }

    @Override
    public Procedure getById(Long id) {
        logger.info("Getting procedure by ID: {}", id);
        Procedure procedure = procedureDAO.getById(id);
        if (procedure == null) {
            logger.warn("Procedure not found with ID: {}", id);
        }
        return procedure;
    }

    @Override
    public List<Procedure> getByCategoryId(Long categoryId) {
        logger.info("Getting procedures by category ID: {}", categoryId);
        List<Procedure> procedures = procedureDAO.getByCategoryId(categoryId);
        logger.info("Found {} procedures with category ID: {}", procedures.size(), categoryId);
        return procedures;
    }

    @Override
    public Procedure getByName(String name) {
        logger.info("Getting procedure by name: {}", name);
        Procedure procedure = procedureDAO.getByName(name);
        if (procedure == null) {
            logger.warn("Procedure not found with name: {}", name);
        }
        return procedure;
    }

    @Override
    public Procedure create(Procedure procedure) {
        logger.info("Creating new procedure with name: {}", procedure.getName());
        procedureDAO.save(procedure);
        Procedure savedProcedure = procedureDAO.getById(procedure.getId());
        if (savedProcedure != null) {
            logger.info("Successfully created procedure with ID: {}", savedProcedure.getId());
        } else {
            logger.error("Failed to create procedure");
        }
        return savedProcedure;
    }

    @Override
    public Procedure update(Procedure procedure) {
        logger.info("Updating procedure with ID: {}", procedure.getId());
        
        if (procedureDAO.getById(procedure.getId()) == null) {
            logger.error("Procedure with ID {} not found for update", procedure.getId());
            throw new IllegalArgumentException("Procedure not found");
        }
        procedureDAO.update(procedure);

        Procedure updatedProcedure = procedureDAO.getById(procedure.getId());
        if (updatedProcedure != null) {
            logger.info("Successfully updated procedure with ID: {}", procedure.getId());
        } else {
            logger.error("Failed to update procedure with ID: {}", procedure.getId());
        }
        return updatedProcedure;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting procedure with ID: {}", id);
        
        if (procedureDAO.getById(id) == null) {
            logger.error("Procedure with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Procedure not found");
        }

        procedureDAO.removeById(id);
        logger.info("Successfully deleted procedure with ID: {}", id);
    }
}
