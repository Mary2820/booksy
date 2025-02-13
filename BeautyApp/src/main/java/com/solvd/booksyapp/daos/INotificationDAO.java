package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface INotificationDAO extends IDAO<Notification>{
    List<Notification> getByClientId(@Param("clientId") Long clientId);
    List<Notification> getByEmployeeId(@Param("employeeId") Long employeeId);
}
