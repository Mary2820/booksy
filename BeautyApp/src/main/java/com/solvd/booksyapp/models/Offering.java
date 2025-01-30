package com.solvd.booksyapp.models;

import java.math.BigDecimal;

public class Offering {
    private Long employeeId;
    private Long procedureId;
    private BigDecimal price;

    public Offering(Long employeeId, Long procedureId, BigDecimal price) {
        this.employeeId = employeeId;
        this.procedureId = procedureId;
        this.price = price;
    }

    public Offering() {}

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
