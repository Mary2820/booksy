package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Procedure;

import java.util.List;

public interface IProcedureDAO extends IDAO<Procedure> {
    List<Procedure> getByCategoryId(Long categoryId);
    Procedure getByName (String name);
}
