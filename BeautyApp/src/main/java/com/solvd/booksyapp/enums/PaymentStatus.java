package com.solvd.booksyapp.enums;

public enum PaymentStatus {
    PENDING("pending"),
    COMPLETED("completed"),
    FAILED("failed"),
    CANCELLED("canceled"),
    IN_PROGRESS("in progress"),
    EXPIRED("expired");

    private final String name;

    PaymentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
