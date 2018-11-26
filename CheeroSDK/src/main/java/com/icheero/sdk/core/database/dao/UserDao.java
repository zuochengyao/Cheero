package com.icheero.sdk.core.database.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.icheero.sdk.core.database.DBHelper;
import com.icheero.sdk.core.database.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserDao implements IBaseDao<User>
{
    public static final String DB_USER = "t_user";

    public static final class t_user
    {
        public static final String ID = "u_id";
        public static final String NAME = "u_name";
        public static final String DATE = "u_date";
    }

    @Override
    public void createTable(SQLiteDatabase db, boolean isExist)
    {
        StringBuilder createSql = new StringBuilder();
        if (isExist)
            createSql.append(DBHelper.TABLE_EXISTS_NOT);
        createSql.append(DBHelper.TABLE_CREATE + DB_USER);
        createSql.append("(");
        createSql.append(t_user.ID + " integer primary key autoincrement,");
        createSql.append(t_user.NAME + " varchar(16),");
        createSql.append(t_user.DATE + " date);");
        db.execSQL(createSql.toString());
    }

    @Override
    public void add(User entity)
    {
        ContentValues cv = new ContentValues(2);
        cv.put(t_user.NAME, "zuochengyao");
        cv.put(t_user.DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
        DBHelper.getInstance().getWritableDatabase().insert(DB_USER, null, cv);
    }

    @Override
    public void delete(User entity)
    {

    }

    @Override
    public void update(User entity)
    {

    }

    @Override
    public User get(int id)
    {
        return null;
    }

    @Override
    public List<User> getAll()
    {
        return null;
    }

}
