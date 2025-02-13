package com.solvd.booksyapp.daos;

import org.apache.ibatis.annotations.Param;

public interface IDAO <T> {
    T getById(@Param("id") Long id);
    T save(@Param("entity") T entity);
    T update(@Param("entity") T entity);
    void removeById(@Param("id") Long id);
}
