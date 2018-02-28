package com.zcy.app.activity.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.zcy.app.R;

public class OptionActivity extends Activity implements PopupMenu.OnMenuItemClickListener, CompoundButton.OnCheckedChangeListener
{
    private MenuItem mOptionItem;
    private CheckBox mFirstOption, mSecondOption;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建menu菜单并初始化
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options, menu);
        mOptionItem = menu.findItem(R.id.menu_option_add);
        mOptionItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener()
        {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item)
            {
                // 必须返回true才能使项展开
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item)
            {
                mFirstOption.setChecked(false);
                mSecondOption.setChecked(false);
                // 必须返回true才能使项折叠
                return true;
            }
        });
        mFirstOption = mOptionItem.getActionView().findViewById(R.id.option_first);
        mFirstOption.setOnCheckedChangeListener(this);
        mSecondOption = mOptionItem.getActionView().findViewById(R.id.option_second);
        mSecondOption.setOnCheckedChangeListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 每次菜单打开时需要调用的设置
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 通过popupMenu点击的回调
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        menuItemSelected(item);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (mFirstOption.isChecked() && mSecondOption.isChecked())
            mOptionItem.collapseActionView(); // 隐藏动作试图
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        menuItemSelected(item);
        return true;
    }

    private void menuItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_option_add:
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_option_edit:
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_option_remove:
                Toast.makeText(this, "Remove", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_option_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
