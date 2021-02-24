package com.icheero.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RefUtils
{
    public static final String CLASS_MAIN_ACTIVITY = "com.icheero.app.MainActivity";

    public static final String CLASS_ACTIVITY_THREAD = "android.app.ActivityThread";
    public static final String CLASS_ACTIVITY_THREAD_APP_BIND_DATA = "android.app.ActivityThread$AppBindData";
    public static final String CLASS_ACTIVITY_THREAD_PROVIDER_CLIENT_RECORD = "android.app.ActivityThread$ProviderClientRecord";
    public static final String CLASS_LOADED_APK = "android.app.LoadedApk";
    public static final String CLASS_CONTENT_PROVIDER = "android.content.ContentProvider";
    public static final String CLASS_SINGLETON = "android.util.Singleton";
    public static final String CLASS_I_ACTIVITY_MANAGER = "android.app.IActivityManager";
    public static final String CLASS_I_ACTIVITY_TASK_MANAGER = "android.app.IActivityTaskManager";
    public static final String CLASS_ACTIVITY_MANAGER = "android.app.ActivityManager";
    public static final String CLASS_ACTIVITY_TASK_MANAGER = "android.app.ActivityTaskManager";
    public static final String CLASS_ACTIVITY_MANAGER_NATIVE = "android.app.ActivityManagerNative";

    public static final String METHOD_GET = "get";
    public static final String METHOD_CURRENT_ACTIVITY_THREAD = "currentActivityThread";

    public static final String FILED_M_INSTANCE = "mInstance";
    public static final String FILED_G_DEFAULT = "gDefault";
    public static final String FILED_I_ACTIVITY_MANAGER_SINGLETON = "IActivityManagerSingleton";
    public static final String FILED_I_ACTIVITY_TASK_MANAGER_SINGLETON = "IActivityTaskManagerSingleton";



    public static Class<?> getClass(String className) throws ClassNotFoundException
    {
        return Class.forName(className);
    }

    public static Object newInstance(String className)
    {
        try
        {
            return getClass(className).newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getField(String className, String filedName)
    {
        try
        {
            Field field = getClass(className).getDeclaredField(filedName);
            field.setAccessible(true);
            return field;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldObject(String className, String filedName)
    {
        try
        {
            Object instance = newInstance(className);
            return getFieldObject(className, instance, filedName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldObject(String className, Object obj, String filedName)
    {
        try
        {
            return getField(className, filedName).get(obj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldObject(String className, String filedName, Object obj, Object filedValue)
    {
        try
        {
            getField(className, filedName).set(obj, filedValue);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Object invokeStaticMethod(String className, String method_name, Class<?>[] pareType, Object[] paramValues)
    {
        return invokeMethod(className, method_name, null, pareType, paramValues);
    }

    public static Object invokeMethod(String className, String method_name, Object obj, Class<?>[] pareType, Object[] paramValues)
    {
        try
        {
            Class<?> clazz = getClass(className);
            Method method = clazz.getDeclaredMethod(method_name, pareType);
            method.setAccessible(true);
            return method.invoke(obj, paramValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getStaticFieldObject(String className, String filedName)
    {
        return getFieldObject(className, null, filedName);
    }

    public static void setStaticObject(String className, String filedName, Object filedValue)
    {
        setFieldObject(className, filedName, null, filedValue);
    }
}
