package com.solvd.booksyapp.services.impl;

import com.solvd.booksyapp.daos.IPaymentDAO;
import com.solvd.booksyapp.models.Payment;
import com.solvd.booksyapp.services.IPaymentService;
import com.solvd.booksyapp.utils.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaymentService implements IPaymentService {
    private static final Logger logger = LogManager.getLogger(PaymentService.class.getName());
    private IPaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = DAOFactory.getPaymentDAO();
    }

    @Override
    public Payment getById(Long id) {
        logger.info("Getting payment by ID: {}", id);
        Payment payment = paymentDAO.getById(id);
        if (payment == null) {
            logger.warn("Payment not found with ID: {}", id);
        }
        return payment;
    }

    @Override
    public Payment getByAppointmentId(Long appointmentId) {
        logger.info("Getting payment by appointment ID: {}", appointmentId);
        Payment payment = paymentDAO.getByAppointmentId(appointmentId);
        if (payment == null) {
            logger.warn("Payment not found with appointment ID: {}", appointmentId);
        }
        return payment;
    }

    @Override
    public Payment create(Payment payment) {
        logger.info("Creating new payment for appointment ID: {}", payment.getAppointmentId());
        paymentDAO.save(payment);
        Payment savedPayment = paymentDAO.getById(payment.getId());
        if (savedPayment != null) {
            logger.info("Successfully created payment with ID: {}", savedPayment.getId());
        } else {
            logger.error("Failed to create payment");
        }
        return savedPayment;
    }

    @Override
    public Payment update(Payment payment) {
        logger.info("Updating payment with ID: {}", payment.getId());
        
        if (paymentDAO.getById(payment.getId()) == null) {
            logger.error("Payment with ID {} not found for update", payment.getId());
            throw new IllegalArgumentException("Payment not found");
        }

        paymentDAO.update(payment);
        Payment updatedPayment = paymentDAO.getById(payment.getId());

        if (updatedPayment != null) {
            logger.info("Successfully updated payment with ID: {}", payment.getId());
        } else {
            logger.error("Failed to update payment with ID: {}", payment.getId());
        }
        return updatedPayment;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting payment with ID: {}", id);
        
        if (paymentDAO.getById(id) == null) {
            logger.error("Payment with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Payment not found");
        }

        paymentDAO.removeById(id);
        logger.info("Successfully deleted payment with ID: {}", id);
    }
}
