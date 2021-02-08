package com.icheero.app.activity.framework.xposed;

import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedMain implements IXposedHookLoadPackage
{
    private static final String TAG = "XposedActivity";
    private static final String PACKAGE_NAME_SOCKET = "java.net.Socket";
    private static final String PACKAGE_NAME_TELEPHONY_MANAGER = "android.telephony.TelephonyManager";

    private static final String METHOD_NAME_GET_DEVICE_ID = "getDeviceId";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable
    {
        Log.i(TAG, "package：" + lpparam.packageName);
        hookMethod(PACKAGE_NAME_TELEPHONY_MANAGER, lpparam.classLoader, "getDeviceId", new XC_MethodHook()
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
        hookConstructor(PACKAGE_NAME_SOCKET, lpparam.classLoader, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable
            {
                Log.i(TAG, "Ready to hook constructor" + param.args[0] + "," + param.args[1]);

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable
            {
                super.afterHookedMethod(param);
                Log.i(TAG, "hook constructor!" + param.args[0] + "," + param.args[1]);
                param.args[0] = "10.155.0.172";
                param.args[1] = 9999;
                Socket socket = (Socket) param.getResult();
                Log.i(TAG, "hook constructor end");
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

    private void hookConstructor(String className, ClassLoader classLoader, Object... paramTypesAndCallback)
    {
        XposedHelpers.findAndHookConstructor(className, classLoader, paramTypesAndCallback);
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
