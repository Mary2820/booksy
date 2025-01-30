package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.User;
import java.util.List;

public interface IUserService {
    User getById(Long id);
    User getByFullName(String firstName, String lastName);
    User getByEmail(String email);
    List<User> getByRoleId(Long roleId);
    User create(User user);
    User update(User user);
    void deleteById(Long id);
}
