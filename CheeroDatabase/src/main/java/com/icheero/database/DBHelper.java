package com.icheero.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.icheero.database.entity.Download;
import com.icheero.database.greendao.DaoMaster;
import com.icheero.database.greendao.DaoSession;
import com.icheero.database.greendao.DownloadDao;
import com.icheero.database.greendao.UserDao;
import com.icheero.database.service.IDownloadService;
import com.icheero.database.service.IUserService;
import com.icheero.util.Log;

import java.util.List;


public class DBHelper implements IDownloadService, IUserService
{
    private static final Class TAG = DBHelper.class;
    private static final String DB_NAME = "cheero.db";

    public static final String TABLE_CREATE = "create table ";
    public static final String TABLE_DROP = "drop table";
    public static final String TABLE_EXISTS_NOT = "if not exists ";
    public static final String TABLE_EXISTS = "if exists ";

    private SQLiteDatabase mSqliteDB;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DownloadDao mDownloadDao;
    private UserDao mUserDao;

    private static volatile DBHelper mInstance;

    private DBHelper()
    {
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

    public void init(Context context)
    {
        mSqliteDB = new DaoMaster.DevOpenHelper(context, DB_NAME, null).getWritableDatabase();
        Log.i(TAG, "Database create: " + (mSqliteDB != null));
        mDaoMaster = new DaoMaster(mSqliteDB);
        mDaoSession = mDaoMaster.newSession();
        mDownloadDao = mDaoSession.getDownloadDao();
        mUserDao = mDaoSession.getUserDao();
    }

    public DownloadDao getDownloadDao()
    {
        return mDownloadDao;
    }

    public UserDao getUserDao()
    {
        return mUserDao;
    }

    public SQLiteDatabase getSqliteDB()
    {
        return mSqliteDB;
    }

    public void closeSqliteDB()
    {
        if (mSqliteDB != null)
        {
            try
            {
                mSqliteDB.close();
            }
            catch (Exception e)
            {
                Log.e(TAG, "Sqlite close error:" + e.getMessage());
            }
        }
    }

    // region IDownloadService
    @Override
    public void insertDownload(Download entity)
    {
        mDaoSession.insertOrReplace(entity);
    }

    @Override
    public void updateDownload(Download entity)
    {
        mDaoSession.update(entity);
    }

    @Override
    public List<Download> getAllDownloadByUrl(String url)
    {
        return mDownloadDao.queryBuilder().where(DownloadDao.Properties.DownloadUrl.eq(url)).orderAsc(DownloadDao.Properties.ThreadId).list();
    }

    // endregion

    // region IUserService
    @Override
    public Cursor getAllUserIds()
    {
        return mSqliteDB.rawQuery("select u_id as _id, * from " + UserDao.TABLENAME, null);
    }
    // endregion
}
