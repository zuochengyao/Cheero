package com.icheero.app.application;

import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.util.Log;


public class MainApplication extends BaseApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        Log.e(MainApplication.class, "onTrimMemory level：" + level);
    }
}
