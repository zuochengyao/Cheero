package com.icheero.plugin.application;

import android.app.Application;
import android.content.Context;

import com.icheero.plugin.framework.andfix.AndFixPatchManager;
import com.icheero.sdk.core.listener.IAppInitListener;
import com.icheero.sdk.core.listener.IAppLifeListener;

import java.util.List;

public class PluginApplication implements IAppInitListener, IAppLifeListener
{
    @Override
    public void injectAppLifecycle(Context context, List<IAppLifeListener> appLifeListeners)
    {
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
        AndFixPatchManager.getInstance().init(application);
    }

    @Override
    public void onTerminate(Application application)
    {

    }
}
