package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Notification;

import java.util.List;

public interface INotificationDAO extends IDAO<Notification>{
    List<Notification> getByClientId(Long clientId);
    List<Notification> getByEmployeeId(Long employeeId);
}
