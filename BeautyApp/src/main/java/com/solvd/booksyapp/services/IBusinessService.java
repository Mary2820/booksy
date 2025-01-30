package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.Business;
import java.util.List;

public interface IBusinessService {
    Business getById(Long id);
    Business getByName(String name);
    List<Business> getByCity(String city);
    List<Business> getByPostCode(String postcode);
    int countByCity(String city);
    Business create(Business business);
    Business update(Business business);
    void deleteById(Long id);
} 