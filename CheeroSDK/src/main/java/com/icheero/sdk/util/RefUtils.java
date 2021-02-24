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

    public static final String FILED_M_H = "mH";
    public static final String FILED_M_INSTANCE = "mInstance";
    public static final String FILED_M_CALLBACK = "mCallback";
    public static final String FILED_G_DEFAULT = "gDefault";
    public static final String FILED_I_ACTIVITY_MANAGER_SINGLETON = "IActivityManagerSingleton";
    public static final String FILED_I_ACTIVITY_TASK_MANAGER_SINGLETON = "IActivityTaskManagerSingleton";
    public static final String FILED_S_CURRENT_ACTIVITY_THREAD = "sCurrentActivityThread";

    /**
     * 获取 Class
     */
    public static Class<?> getClass(String className) throws ClassNotFoundException
    {
        return Class.forName(className);
    }

    /**
     * 根据 classname 创建默认实例对象
     */
    public static Object newInstance(String className)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class<?> clazz = getClass(className);
        return clazz.newInstance();
    }

    /**
     * 根据 classname 获取 所有成员变量，不包括基类
     *
     * @param filedName 要获取的成员变量名称
     * @return 属性字段
     */
    public static Field getDeclaredField(String className, String filedName)
            throws ClassNotFoundException, NoSuchFieldException
    {
        Class<?> clazz = getClass(className);
        return getDeclaredField(clazz, filedName);
    }

    /**
     * 根据 clazz 获取 所有成员变量，不包括基类
     *
     * @param filedName 要获取的成员变量名称
     * @return 属性字段
     */
    public static Field getDeclaredField(Class<?> clazz, String filedName) throws NoSuchFieldException
    {
        Field field = clazz.getDeclaredField(filedName);
        field.setAccessible(true);
        return field;
    }

    /**
     * 根据 classname，获取该类中静态字段 field 的属性值
     *
     * @param className 类名称
     * @param filedName 属性名称
     * @return 属性值
     */
    public static Object getStaticDeclaredFieldValue(String className, String filedName)
            throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException
    {
        return getObjectDeclaredFieldValue(className, filedName, null);
    }

    /**
     * 获取 class 类中静态字段 field 的属性值
     *
     * @param clazz 类名称
     * @param filedName 属性名称
     * @return 属性值
     */
    public static Object getStaticDeclaredFieldValue(Class<?> clazz, String filedName)
            throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException
    {
        return getObjectDeclaredFieldValue(clazz, filedName, null);
    }

    /**
     * 创建 className 的实例对象，并获取该对象的 Field 属性值
     *
     * @param filedName 属性名称
     * @param className 类名称
     * @return 属性值
     */
    public static Object getObjectDeclaredFieldValue(String className, String filedName)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException
    {
        Object instance = newInstance(className);
        return getObjectDeclaredFieldValue(className, filedName, instance);
    }

    /**
     * 根据 classname 获取该 obj 对象的 Field 属性值
     *
     * @param className 类名称
     * @param filedName 属性名称
     * @param obj 对象实例
     * 如果字段不是静态字段的话,要传入反射类的对象.如果传null是会报java.lang.NullPointerException
     * 如果字段是静态字段的话,传入任何对象都是可以的,包括null
     * @return 属性值
     */
    public static Object getObjectDeclaredFieldValue(String className, String filedName, Object obj)
            throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException
    {
        Field field = getDeclaredField(className, filedName);
        return field.get(obj);
    }

    /**
     * 根据 class 获取该 obj 对象的 Field 属性值
     *
     * @param clazz class类
     * @param filedName 属性名称
     * @param obj 对象实例
     * @return 属性值
     */
    public static Object getObjectDeclaredFieldValue(Class<?> clazz, String filedName, Object obj)
    {
        try
        {
            Field field = getDeclaredField(clazz, filedName);
            return field.get(obj);
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
            getDeclaredField(className, filedName).set(obj, filedValue);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void setFieldObject(Class<?> clazz, String filedName, Object obj, Object filedValue)
    {
        try
        {
            getDeclaredField(clazz, filedName).set(obj, filedValue);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Object invokeStaticMethod(String className, String method_name, Class<?>[] pareType,
            Object[] paramValues)
    {
        return invokeMethod(className, method_name, null, pareType, paramValues);
    }

    public static Object invokeMethod(String className, String method_name, Object obj, Class<?>[] pareType,
            Object[] paramValues)
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

    public static void setStaticObject(String className, String filedName, Object filedValue)
    {
        setFieldObject(className, filedName, null, filedValue);
    }
}
