package com.icheero.sdk.core.database.dao;

import android.database.sqlite.SQLiteDatabase;

import com.icheero.sdk.core.database.DBHelper;
import com.icheero.sdk.core.database.entity.Download;

import java.util.List;

public class DownloadDao implements IBaseDao<Download>
{
    public static final String DB_DOWNLOAD = "t_download";


    public static final class t_download
    {
        public static final String ID = "d_id";
        public static final String POSITION_PROGRESS = "d_position_progress";
        public static final String POSITION_START = "d_position_start";
        public static final String POSITION_EDN = "d_position_end";
        public static final String DOWNLOAD_URL = "d_download_url";
        public static final String THREAD_ID = "d_thread_id";
    }

    @Override
    public void createTable(SQLiteDatabase db, boolean isExist)
    {
        StringBuilder createSql = new StringBuilder();
        if (isExist)
            createSql.append(DBHelper.TABLE_EXISTS_NOT);
        createSql.append(DBHelper.TABLE_CREATE + DB_DOWNLOAD);
        createSql.append("(");
        createSql.append(t_download.ID + " integer primary key autoincrement,");
        createSql.append(t_download.POSITION_PROGRESS + " integer,");
        createSql.append(t_download.POSITION_START + " integer,");
        createSql.append(t_download.POSITION_EDN + " integer,");
        createSql.append(t_download.DOWNLOAD_URL + " text,");
        createSql.append(t_download.THREAD_ID + " integer)");
        db.execSQL(createSql.toString());
    }

    @Override
    public void add(Download entity)
    {
    }

    @Override
    public void delete(Download entity)
    {

    }

    @Override
    public void update(Download entity)
    {

    }

    @Override
    public Download get(int id)
    {
        return null;
    }

    @Override
    public List<Download> getAll()
    {
        return null;
    }

}
