package com.solvd.booksyapp.services;

import com.solvd.booksyapp.models.Procedure;

import java.util.List;

public interface IProcedureService {
    Procedure getById(Long id);
    List<Procedure> getByCategoryId(Long categoryId);
    Procedure getByName (String name);
    Procedure create(Procedure procedure);
    Procedure update(Procedure procedure);
    void deleteById(Long id);
}
