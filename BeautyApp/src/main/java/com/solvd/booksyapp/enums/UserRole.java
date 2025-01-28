package com.solvd.booksyapp.enums;

public enum UserRole {
    CLIENT(1L, "client"),
    EMPLOYEE(2L, "employee");

    private final Long id;
    private final String name;

    UserRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static UserRole getById(int id) {
        for (UserRole userRole : values()) {
            if (userRole.getId() == id) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No user role with ID: " + id);
    }

    public static UserRole getByName(String name) {
        for (UserRole userRole : values()) {
            if (userRole.getName().equalsIgnoreCase(name)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No user role with name: " + name);
    }
}
