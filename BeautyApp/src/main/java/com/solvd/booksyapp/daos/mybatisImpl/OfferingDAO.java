package com.solvd.booksyapp.daos.mybatisImpl;

import com.solvd.booksyapp.daos.IOfferingDAO;
import com.solvd.booksyapp.models.Offering;
import com.solvd.booksyapp.utils.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OfferingDAO implements IOfferingDAO {
    @Override
    public Offering getByEmployeeIdAndProcedureId(Long employeeId, Long procedureId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IOfferingDAO mapper = session.getMapper(IOfferingDAO.class);
            return mapper.getByEmployeeIdAndProcedureId(employeeId, procedureId);
        }
    }

    @Override
    public Offering save(Offering offering) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IOfferingDAO mapper = session.getMapper(IOfferingDAO.class);
            mapper.save(offering);
            session.commit();
            return offering;
        }
    }

    @Override
    public Offering updatePrice(Offering offering) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IOfferingDAO mapper = session.getMapper(IOfferingDAO.class);
            mapper.updatePrice(offering);
            session.commit();
            return offering;
        }
    }

    @Override
    public void remove(Long employeeId, Long procedureId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IOfferingDAO mapper = session.getMapper(IOfferingDAO.class);
            mapper.remove(employeeId, procedureId);
            session.commit();
        }
    }

    @Override
    public List<Offering> getByEmployeeId(Long employeeId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IOfferingDAO mapper = session.getMapper(IOfferingDAO.class);
            return mapper.getByEmployeeId(employeeId);
        }
    }

    @Override
    public List<Offering> getByBusinessId(Long businessId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IOfferingDAO mapper = session.getMapper(IOfferingDAO.class);
            return mapper.getByBusinessId(businessId);
        }
    }

    @Override
    public List<Offering> getByProcedureId(Long procedureId) {
        try (SqlSession session = ConnectionFactory.getSQLSessionFactory().openSession()) {
            IOfferingDAO mapper = session.getMapper(IOfferingDAO.class);
            return mapper.getByProcedureId(procedureId);
        }
    }
} 