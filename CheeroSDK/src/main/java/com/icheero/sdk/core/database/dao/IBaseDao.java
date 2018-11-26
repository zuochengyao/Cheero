package com.icheero.sdk.core.database.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface IBaseDao<T>
{
    void createTable(SQLiteDatabase db, boolean isExist);

    void add(T entity);

    void delete(T entity);

    void update(T entity);

    T get(int id);

    List<T> getAll();
}
