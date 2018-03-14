package com.zcy.sdk.data.db;

public class DBDefine
{
    protected static final String DBNAME = "zcydb";
    protected static final int DBVERSION = 2;

    protected static String Tables[] = {
        DBDefine.DB_USER
    };

    /**
     *
     */
    public static final String DB_USER = "t_user";

    public static final class t_user
    {
        public static final String ID = "u_id";
        public static final String NAME = "u_name";
        public static final String DATE = "u_date";
    }

    private static final String CREATE = "create table if not exists ";

    public static final String createTableUser()
    {
        StringBuilder createSql = new StringBuilder();
        createSql.append(CREATE + DB_USER);
        createSql.append("(");
        createSql.append(t_user.ID + " integer primary key autoincrement,");
        createSql.append(t_user.NAME + " varchar(16),");
        createSql.append(t_user.DATE + " date);");
        return createSql.toString();
    }

}
