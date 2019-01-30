package com.icheero.app.application;

import android.content.Context;

import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.core.manager.ApplicationManager;
import com.icheero.util.Log;


public class MainApplication extends BaseApplication
{
    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        ApplicationManager.getInstance().attachBaseContext(base);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        ApplicationManager.getInstance().onCreate(this);
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        Log.e(MainApplication.class, "onTrimMemory level：" + level);
    }
}
