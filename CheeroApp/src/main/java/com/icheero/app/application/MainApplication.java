package com.icheero.app.application;

import android.app.Application;

import com.icheero.common.base.CheeroNative;
import com.icheero.common.util.Log;


public class MainApplication extends Application
{
    public static final int TRACE_MODE_ON_SCREEN = 0;
    // public static final int TRACE_MODE_ON_FILE = 1;
    // public static final int TRACE_MODE_OFF = 2;

    @Override
    public void onCreate()
    {
        super.onCreate();
        CheeroNative.nativeSetTraceMode(TRACE_MODE_ON_SCREEN);
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        Log.e(MainApplication.class, "onTrimMemory level：" + level);
    }
}
