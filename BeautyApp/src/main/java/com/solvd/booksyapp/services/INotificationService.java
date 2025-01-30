package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.Notification;

import java.util.List;

public interface INotificationService {
    Notification getById(Long id);
    List<Notification> getByClientId(Long clientId);
    List<Notification> getByEmployeeId(Long employeeId);
    Notification create(Notification notification);
    Notification update(Notification notification);
    void deleteById(Long id);
}
