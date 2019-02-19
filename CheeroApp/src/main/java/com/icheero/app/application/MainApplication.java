package com.icheero.app.application;

import android.content.Context;
import android.content.res.Configuration;

import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.core.manager.ApplicationManager;


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
    public void onTerminate()
    {
        super.onTerminate();
        ApplicationManager.getInstance().onTerminate(this);
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        ApplicationManager.getInstance().onTrimMemory(level);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        ApplicationManager.getInstance().onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ApplicationManager.getInstance().onConfigurationChanged(newConfig);
    }
}
