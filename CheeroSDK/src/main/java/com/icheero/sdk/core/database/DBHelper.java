package com.icheero.sdk.core.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper
{
    private SQLiteDatabase db;
    public DBHelper(Context context)
    {
        super(context, DBDefine.DBNAME, null, DBDefine.DBVERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DBDefine.createTableUser());
        ContentValues cv = new ContentValues(2);
        cv.put(DBDefine.t_user.NAME, "zuochengyao");
        cv.put(DBDefine.t_user.DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        db.insert(DBDefine.DB_USER, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DBDefine.createTableUser());
        onCreate(db);
    }

    public SQLiteDatabase getDatabase()
    {
        return db;
    }
}
