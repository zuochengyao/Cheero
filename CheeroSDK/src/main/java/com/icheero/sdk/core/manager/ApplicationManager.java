package com.icheero.sdk.core.manager;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.icheero.sdk.core.listener.IAppInitListener;
import com.icheero.sdk.core.listener.IAppLifeListener;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager implements IAppLifeListener
{
    private List<IAppLifeListener> mAppLifeListeners;
    private List<Application.ActivityLifecycleCallbacks> mActivityLifeCallbacks;

    private static volatile ApplicationManager mInstance;

    private ApplicationManager()
    {
        mAppLifeListeners = new ArrayList<>();
        mActivityLifeCallbacks = new ArrayList<>();
    }

    public static ApplicationManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ApplicationManager.class)
            {
                if (mInstance == null)
                    mInstance = new ApplicationManager();
            }
        }
        return mInstance;
    }

    @Override
    public void attachBaseContext(Context context)
    {
        // 初始化Manifest文件解析器，用于解析组件在自己的Manifest文件配置的Application
        ManifestManager parser = new ManifestManager(context);
        List<IAppInitListener> appInitListeners = parser.parse();
        // 解析得到的组件Application列表之后，给每个组件Application注入context
        // 和Application的生命周期的回调，用于实现application的同步
        if (appInitListeners != null && appInitListeners.size() > 0)
        {
            for (IAppInitListener appInit : appInitListeners)
            {
                appInit.injectAppLifecycle(context, mAppLifeListeners);
                appInit.injectActivityLifecycle(context, mActivityLifeCallbacks);
            }
        }
        if (mAppLifeListeners != null && mAppLifeListeners.size() > 0)
        {
            for (IAppLifeListener appLife : mAppLifeListeners)
                appLife.attachBaseContext(context);
        }
    }

    @Override
    public void onCreate(Application application)
    {
        //  相应调用组件Application代理类的onCreate方法
        if (mAppLifeListeners != null && mAppLifeListeners.size() > 0)
        {
            for (IAppLifeListener appLife : mAppLifeListeners)
                appLife.onCreate(application);
        }
        if (mActivityLifeCallbacks != null && mActivityLifeCallbacks.size() > 0)
        {
            for (Application.ActivityLifecycleCallbacks callback : mActivityLifeCallbacks)
                application.registerActivityLifecycleCallbacks(callback);
        }
    }

    @Override
    public void onTerminate(Application application)
    {
        //  相应调用组件Application代理类的onTerminate方法
        if (mAppLifeListeners != null && mAppLifeListeners.size() > 0)
        {
            for (IAppLifeListener appLife : mAppLifeListeners)
                appLife.onTerminate(application);
        }
        if (mActivityLifeCallbacks != null && mActivityLifeCallbacks.size() > 0)
        {
            for (Application.ActivityLifecycleCallbacks life : mActivityLifeCallbacks)
                application.unregisterActivityLifecycleCallbacks(life);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        if (mAppLifeListeners != null && mAppLifeListeners.size() > 0)
        {
            for (IAppLifeListener appLife : mAppLifeListeners)
                appLife.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onTrimMemory(int level)
    {
        if (mAppLifeListeners != null && mAppLifeListeners.size() > 0)
        {
            for (IAppLifeListener appLife : mAppLifeListeners)
                appLife.onTrimMemory(level);
        }
    }

    @Override
    public void onLowMemory()
    {
        if (mAppLifeListeners != null && mAppLifeListeners.size() > 0)
        {
            for (IAppLifeListener appLife : mAppLifeListeners)
                appLife.onLowMemory();
        }
    }
}
