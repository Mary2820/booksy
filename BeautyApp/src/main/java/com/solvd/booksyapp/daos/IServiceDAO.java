package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Service;

import java.util.List;

public interface IServiceDAO extends IDAO<Service> {
    List<Service> getByCategoryId(Long categoryId);
    Service getByName (String name);
}
