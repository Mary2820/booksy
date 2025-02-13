package com.solvd.booksyapp.daos;

import org.apache.ibatis.annotations.Param;

public interface IDAO <T> {
    T getById(@Param("id") Long id);
    void save(@Param("entity") T entity);
    void update(@Param("entity") T entity);
    void removeById(@Param("id") Long id);
}
