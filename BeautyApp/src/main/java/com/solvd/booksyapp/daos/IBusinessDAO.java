package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Business;

import java.util.List;

public interface IBusinessDAO extends IDAO<Business> {
    List<Business> getByName(String name);
    List<Business> getByCity(String city);
    List<Business> getByPostCode(String postcode);
    int countByCity(String city);
}
