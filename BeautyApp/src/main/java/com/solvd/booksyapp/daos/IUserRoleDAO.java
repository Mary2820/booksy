package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.UserRole;

public interface IUserRoleDAO extends IDAO<UserRole>{
    UserRole getByName(String name);
}
