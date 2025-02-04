package com.solvd.booksyapp.models;

import java.math.BigDecimal;

public class Employee {
    private Long id;
    private Long userId;
    private String description;
    private BigDecimal rating;
    private Long businessId;

    public Employee(Long businessId, BigDecimal rating, String description, Long userId) {
        this.businessId = businessId;
        this.rating = rating;
        this.description = description;
        this.userId = userId;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", businessId=" + businessId +
                '}';
    }
}
