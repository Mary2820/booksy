package com.solvd.booksyapp.enums;

public enum AppointmentStatus {
    PENDING("pending"),
    SCHEDULED("scheduled"),
    CANCELLED("canceled"),
    COMPLETED("completed");

    private final String name;

    AppointmentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
