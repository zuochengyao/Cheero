package com.icheero.app.activity.framework.xposed;

import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedMain implements IXposedHookLoadPackage
{
    private static final String TAG = "XposedActivity";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable
    {
        Log.i(TAG, "package：" + lpparam.packageName);
        hookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDeviceId", new XC_MethodHook()
        {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable
            {
                super.beforeHookedMethod(param);
                Log.i(TAG, "Ready to hook");
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable
            {
                super.afterHookedMethod(param);
                Log.i(TAG, "hook getDeviceId...");
                Object obj = param.getResult();
                Log.i(TAG, "imei args:" + obj);
                param.setResult("Cheero");
            }
        });
    }

    private void hookMethod(String className, ClassLoader classLoader, String methodName, Object... paramTypesAndCallback)
    {
        try
        {
            XposedHelpers.findAndHookMethod(className, classLoader, methodName, paramTypesAndCallback);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void hookMethod(String className, String methodName, XC_MethodHook hook)
    {
        try
        {
            Class clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods())
            {
                if (method.getName().equals(methodName) && !Modifier.isAbstract(method.getModifiers()) && Modifier.isPublic(method.getModifiers()))
                {
                    XposedBridge.hookMethod(method, hook);
                }
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
