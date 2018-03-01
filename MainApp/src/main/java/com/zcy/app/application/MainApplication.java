package com.zcy.app.application;

import android.app.Application;

import com.zcy.sdk.ndk.JniNative;

/**
 * Created by zuochengyao on 2018/3/1.
 */

public class MainApplication extends Application
{
    public static final int TRACE_MODE_ON_SCREEN = 0;
    // public static final int TRACE_MODE_ON_FILE = 1;
    // public static final int TRACE_MODE_OFF = 2;

    @Override
    public void onCreate()
    {
        super.onCreate();
        JniNative.serviceSetTraceMode(TRACE_MODE_ON_SCREEN);
    }
}
