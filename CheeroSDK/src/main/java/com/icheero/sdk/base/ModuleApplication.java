package com.icheero.sdk.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.icheero.sdk.core.listener.IAppInitListener;
import com.icheero.sdk.core.listener.IAppLifeListener;
import com.icheero.sdk.util.Log;

import java.util.List;

public class ModuleApplication implements IAppInitListener, IAppLifeListener
{
    protected Class TAG;

    public ModuleApplication()
    {
        TAG = getClass();
    }

    @Override
    public void injectAppLifecycle(Context context, List<IAppLifeListener> appLifeListeners)
    {
        Log.i(TAG, "injectAppLifecycle");
        appLifeListeners.add(this);
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> activityLifecycleCallbacks)
    {
    }

    @Override
    public void attachBaseContext(Context context)
    {
    }

    @Override
    public void onCreate(Application application)
    {
    }

    @Override
    public void onTerminate(Application application)
    {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
    }

    @Override
    public void onLowMemory()
    {
    }

    @Override
    public void onTrimMemory(int level)
    {
    }
}
