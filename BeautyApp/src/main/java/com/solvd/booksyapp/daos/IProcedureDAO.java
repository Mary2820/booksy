package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Procedure;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProcedureDAO extends IDAO<Procedure> {
    List<Procedure> getByCategoryId(@Param("categoryId") Long categoryId);
    Procedure getByName(@Param("name") String name);
}
