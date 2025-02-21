package com.solvd.booksyapp.models.builders;

import com.solvd.booksyapp.models.User;

public class UserBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Long roleId;

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public User build() {
        return new User(id, firstName, lastName, email, phone, password, roleId);
    }
} 