package com.icheero.common.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.icheero.common.manager.ViewManager;


public class BaseActivity extends AppCompatActivity
{
    // region Activity's Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ViewManager.getInstance().removeActivity(this);
    }

    // endregion



    // region Extra Methods
    protected <T extends View> T $(@IdRes int resId)
    {
        return super.findViewById(resId);
    }
    // endregion
}
