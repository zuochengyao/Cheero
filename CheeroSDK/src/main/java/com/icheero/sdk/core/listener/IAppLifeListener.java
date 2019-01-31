package com.icheero.sdk.core.listener;

import android.app.Application;
import android.content.Context;

/**
 * @author 左程耀 2019年01月29日
 *
 * Application生命周期监听器
 */
public interface IAppLifeListener
{
    void attachBaseContext(Context context);

    void onCreate(Application application);

    void onTerminate(Application application);

    /*
    void onCreate();

    void onLowMemory();

    void onTrimMemory(int level);

    void onTerminate();

    void onConfigurationChanged(Configuration newConfig);

    void onBaseContextAttached(Context base);
    */
}
