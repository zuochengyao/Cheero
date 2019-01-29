package com.icheero.sdk.core.listener;

import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * @author 左程耀 2019年01月29日
 * Application初始化监听器
 */
public interface IAppInitListener
{
    void injectAppLifecycle(Context context, List<IAppLifeListener> appLifeListeners);

    void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> activityLifecycleCallbacks);
}
