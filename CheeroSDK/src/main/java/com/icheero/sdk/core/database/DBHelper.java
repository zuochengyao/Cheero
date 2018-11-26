package com.icheero.sdk.core.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.core.database.dao.DownloadDao;
import com.icheero.sdk.core.database.dao.UserDao;
import com.icheero.sdk.util.Log;

public class DBHelper extends SQLiteOpenHelper
{
    private static final Class TAG = DBHelper.class;
    private static final String DB_NAME = "cheero.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_CREATE = "create table ";
    public static final String TABLE_DROP = "drop table";
    public static final String TABLE_EXISTS_NOT = "if not exists ";
    public static final String TABLE_EXISTS = "if exists ";

    private UserDao mUserDao;
    private DownloadDao mDownloadDao;
    private SQLiteDatabase mSqliteDB;

    private static volatile DBHelper mInstance;

    private DBHelper()
    {
        super(BaseApplication.getAppInstance(), DB_NAME, null, DB_VERSION);
        mUserDao = new UserDao();
        mDownloadDao = new DownloadDao();
        this.mSqliteDB = getWritableDatabase();
    }

    public static DBHelper getInstance()
    {
        if (mInstance == null)
        {
            synchronized (TAG)
            {
                if (mInstance == null)
                    mInstance = new DBHelper();
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(TAG, "DB onCreate!");
        mUserDao.createTable(db, false);
        mDownloadDao.createTable(db, false);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(TAG, "DB onUpgrade! oldVersion:" + oldVersion + ", newVersion:" + newVersion);
        mUserDao.createTable(db, true);
        mDownloadDao.createTable(db, true);
    }

}
