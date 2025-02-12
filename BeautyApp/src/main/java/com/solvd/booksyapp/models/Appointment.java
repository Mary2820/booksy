package com.solvd.booksyapp.models;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.solvd.booksyapp.utils.jaxb.adapters.LocalDateAdapter;
import com.solvd.booksyapp.utils.jaxb.adapters.LocalDateTimeAdapter;
import com.solvd.booksyapp.utils.jaxb.adapters.LocalTimeAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonRootName(value = "appointment")
@XmlRootElement(name = "appointment")
@XmlAccessorType(XmlAccessType.NONE)
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

    @XmlAttribute(name = "id")
    public Long getId() {
        return id;
    }

    @XmlElement(name = "clientId")
    public Long getClientId() {
        return clientId;
    }

    @XmlElement(name = "procedureId")
    public Long getProcedureId() {
        return procedureId;
    }

    @XmlElement(name = "employeeId")
    public Long getEmployeeId() {
        return employeeId;
    }

    @XmlElement(name = "status")
    public String getStatus() {
        return status;
    }

    @XmlElement(name = "createdAt")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @XmlElement(name = "date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    @XmlElement(name = "dayOfWeek")
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @XmlElement(name = "startTime")
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getStartTime() {
        return startTime;
    }

    @XmlElement(name = "endTime")
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
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
