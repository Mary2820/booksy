package com.solvd.booksyapp.enums;

public enum NotificationStatus {
    SENT("sent"),
    DELIVERED("delivered"),
    READ("read"),
    FAILED("failed");

    private final String name;

    NotificationStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
