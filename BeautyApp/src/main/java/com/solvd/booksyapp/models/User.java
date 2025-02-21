package com.solvd.booksyapp.models;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.*;
import com.solvd.booksyapp.models.builders.UserBuilder;

@JsonRootName(value = "user")
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Long roleId;

    public User(Long id, String firstName, String lastName, String email, String phone, String password, Long roleId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.roleId = roleId;
    }

    public User() {
    }

    @XmlAttribute(name = "id")
    public Long getId() {
        return id;
    }

    @XmlElement(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    @XmlElement(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    @XmlElement(name = "email")
    public String getEmail() {
        return email;
    }

    @XmlElement(name = "phone")
    public String getPhone() {
        return phone;
    }

    @XmlElement(name = "password")
    public String getPassword() {
        return password;
    }

    @XmlElement(name = "roleId")
    public Long getRoleId() {
        return roleId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
