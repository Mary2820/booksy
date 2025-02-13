package com.solvd.booksyapp.daos;

import com.solvd.booksyapp.models.Business;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBusinessDAO extends IDAO<Business> {
    Business getByName(@Param("name") String name);
    List<Business> getByCity(@Param("city") String city);
    List<Business> getByPostCode(@Param("postcode") String postcode);
    int countByCity(@Param("city") String city);
}
