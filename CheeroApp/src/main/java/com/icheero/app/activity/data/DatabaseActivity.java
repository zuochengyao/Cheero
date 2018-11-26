package com.icheero.app.activity.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.database.DBHelper;
import com.icheero.sdk.core.database.dao.UserDao;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class DatabaseActivity extends BaseActivity
{
    @BindView(R.id.db_name)
    EditText mText;
    @BindView(R.id.db_add)
    Button mAddBtn;
    @BindView(R.id.db_list)
    ListView mList;

    DBHelper mHelper;
    SQLiteDatabase db;
    Cursor mCursor;
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_database);
        super.onCreate(savedInstanceState);
        mHelper = DBHelper.getInstance();
        db = mHelper.getWritableDatabase();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mCursor = db.rawQuery("select u_id as _id, * from " + UserDao.DB_USER, null);
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, mCursor, new String[]{UserDao.t_user.NAME, UserDao.t_user.DATE}, new int[]{android.R.id.text1, android.R.id.text2});
        mList.setAdapter(mAdapter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        db.close();
        mCursor.close();
    }

    @OnClick(R.id.db_add)
    public void OnClickEvent()
    {
        ContentValues cv = new ContentValues(2);
        cv.put(UserDao.t_user.NAME, mText.getText().toString());
        cv.put(UserDao.t_user.DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        db.insert(UserDao.DB_USER, null, cv);
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
        mText.setText(null);
    }

    @OnItemClick(R.id.db_list)
    public void OnItemClickEvent(int position)
    {
        mCursor.moveToPosition(position);
        String rowId = mCursor.getString(0);
        db.delete(UserDao.DB_USER, "_id = ? ", new String[]{rowId});
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
    }
}
