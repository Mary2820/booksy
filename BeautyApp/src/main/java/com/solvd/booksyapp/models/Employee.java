package com.solvd.booksyapp.models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

import java.math.BigDecimal;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.NONE)
public class Employee {
    private Long id;
    private Long userId;
    private String description;
    private BigDecimal rating;
    private Long businessId;

    public Employee() {}

    public Employee(Long userId, String description, BigDecimal rating, Long businessId) {
        this.userId = userId;
        this.description = description;
        this.rating = rating;
        this.businessId = businessId;
    }

    @XmlAttribute(name = "id")
    public Long getId() {
        return id;
    }

    @XmlElement(name = "userId")
    public Long getUserId() {
        return userId;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    @XmlElement(name = "rating")
    public BigDecimal getRating() {
        return rating;
    }

    @XmlElement(name = "businessId")
    public Long getBusinessId() {
        return businessId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
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
