package com.solvd.booksyapp.daos;

public interface IDAO <T> {
    T getById (Long id);
    T save (T entity);
    T update (T entity);
    void removeById (Long id);
}
