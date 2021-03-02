package com.icheero.plugin.load.proxy;

import android.content.Intent;

import com.icheero.plugin.load.GlobalActivityHookHelper;
import com.icheero.sdk.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityManagerProxy implements InvocationHandler
{
    private static final Class<?> TAG = IActivityManagerProxy.class;
    private static final String METHOD_START_ACTIVITY = "startActivity";
    private static final String NAME_PACKAGE = "com.icheero.app";
    private static final String NAME_PLUGIN_ACTIVITY = ".activity.plugin.PluginActivity";

    private final Object mActivityManager;
    private final String packageName;
    private final String clz;

    public IActivityManagerProxy(Object activityManager)
    {
        this(activityManager, NAME_PACKAGE, NAME_PLUGIN_ACTIVITY);
    }

    public IActivityManagerProxy(Object activityManager, String packageName, String clz)
    {
        mActivityManager = activityManager;
        this.packageName = packageName;
        this.clz = clz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        Log.i(TAG, "invoke: " + method.getName());
        if (METHOD_START_ACTIVITY.equals(method.getName()))
        {
            Intent intent = null;
            int index = 0;
            for (Object arg : args)
            {
                if (arg instanceof Intent)
                {
                    intent = (Intent) arg;
                    break;
                }
                index++;
            }
            Intent pluginIntent = new Intent();
            pluginIntent.setClassName(packageName, packageName + clz);
            pluginIntent.putExtra(GlobalActivityHookHelper.TARGET_INTENT, intent);
            args[index] = pluginIntent;
        }
        return method.invoke(mActivityManager, args);
    }
}
