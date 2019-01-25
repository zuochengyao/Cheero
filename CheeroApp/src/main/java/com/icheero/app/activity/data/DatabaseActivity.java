package com.icheero.app.activity.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.icheero.app.R;
import com.icheero.database.DBHelper;
import com.icheero.database.greendao.UserDao;
import com.icheero.sdk.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.icheero.database.greendao.UserDao.Properties.Date;
import static com.icheero.database.greendao.UserDao.Properties.Name;

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
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mCursor = mHelper.getAllUserIds();
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, mCursor, new String[]{Name.columnName, Date.name}, new int[]{
                android.R.id.text1, android.R.id.text2
        });
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
        //mHelper.insert(new User(mText.getText().toString(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date())));
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
        mText.setText(null);
    }

    @OnItemClick(R.id.db_list)
    public void OnItemClickEvent(int position)
    {
        mCursor.moveToPosition(position);
        String rowId = mCursor.getString(0);
        db.delete(UserDao.TABLENAME, "_id = ? ", new String[]{rowId});
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
    }
}
