package com.solvd.jaxbdemo.wrappers;

import com.solvd.booksyapp.models.*;
import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "booksy_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class BooksyDataWrapper {
    
    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<User> users;
    
    @XmlElementWrapper(name = "businesses")
    @XmlElement(name = "business")
    private List<Business> businesses;
    
    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee")
    private List<Employee> employees;
    
    @XmlElementWrapper(name = "procedures")
    @XmlElement(name = "procedure")
    private List<Procedure> procedures;
    
    @XmlElementWrapper(name = "appointments")
    @XmlElement(name = "appointment")
    private List<Appointment> appointments;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
