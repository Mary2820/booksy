package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.User;

import java.util.List;

public interface IUserDAO extends IDAO<User> {
    User getUserByFullName(String firstName, String lastName);
    User getUserByEmail(String email);
    List<User> getByRoleId(Long roleId);
}
