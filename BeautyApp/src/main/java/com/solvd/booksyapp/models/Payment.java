package com.solvd.booksyapp.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private Long id;
    private Long appointmentId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String status;

    public Payment(Long appointmentId, BigDecimal amount, LocalDate paymentDate, String status) {
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public Payment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
