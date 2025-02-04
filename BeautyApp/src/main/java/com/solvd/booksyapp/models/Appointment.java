package com.solvd.booksyapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
    private Long id;
    private Long clientId;
    private Long procedureId;
    private Long employeeId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDate date;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public Appointment(Long clientId, Long procedureId, Long employeeId, String status, LocalDate date,
                       String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.clientId = clientId;
        this.procedureId = procedureId;
        this.employeeId = employeeId;
        this.status = status;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Appointment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", procedureId=" + procedureId +
                ", employeeId=" + employeeId +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", date=" + date +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
