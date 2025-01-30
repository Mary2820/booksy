package com.solvd.booksyapp.models;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private Long appointmentId;
    private String message;
    private String status;
    private LocalDateTime createdAt;

    public Notification(Long appointmentId, String message, String status) {
        this.appointmentId = appointmentId;
        this.message = message;
        this.status = status;
    }

    public Notification() {}

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
