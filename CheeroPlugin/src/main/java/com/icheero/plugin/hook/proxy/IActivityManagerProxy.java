package com.icheero.plugin.hook.proxy;

import android.content.ComponentName;
import android.content.Intent;

import com.icheero.plugin.hook.GlobalActivityHookHelper;
import com.icheero.sdk.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityManagerProxy implements InvocationHandler
{
    private static final Class<?> TAG = IActivityManagerProxy.class;
    private static final String METHOD_START_ACTIVITY = "startActivity";
    private static final String NAME_PACKAGE = "com.icheero.app";
    private static final String NAME_PLUGIN_ACTIVITY = "com.icheero.plugin.activity.LoadMegliveActivity";

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
            if (intent.getComponent().getClassName().equals(
                    "com.megvii.meglive_still.demo.MainActivity"))
            {
                Intent pluginIntent = new Intent();
                pluginIntent.setComponent(new ComponentName(packageName, clz));
                // pluginIntent.setClassName(packageName, packageName + clz);
                pluginIntent.putExtra(GlobalActivityHookHelper.TARGET_INTENT, intent);
                args[index] = pluginIntent;
            }
        }
        return method.invoke(mActivityManager, args);
    }
}
