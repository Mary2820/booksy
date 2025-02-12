package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserDAO extends IDAO<User> {
    User getUserByFullName(@Param("firstName") String firstName,
                           @Param("lastName") String lastName);
    User getUserByEmail(@Param("email") String email);
    List<User> getByRoleId(@Param("roleId") Long roleId);
    User save(@Param("user") User user);
}
