package com.icheero.sdk.core.plugin;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.RefInvoke;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookHelper
{
    public static final String TARGET_INTENT = "target_intent";
    public static final String CLASS_NAME_SINGLETON = "android.util.Singleton";
    public static final String CLASS_NAME_I_ACTIVITY_MANAGER = "android.app.IActivityManager";
    public static final String CLASS_NAME_I_ACTIVITY_TASK_MANAGER = "android.app.IActivityTaskManager";
    public static final String CLASS_NAME_ACTIVITY_MANAGER = "android.app.ActivityManager";
    public static final String CLASS_NAME_ACTIVITY_TASK_MANAGER = "android.app.ActivityTaskManager";
    public static final String CLASS_NAME_ACTIVITY_MANAGER_NATIVE = "android.app.ActivityManagerNative";

    public static final String FILED_NAME_M_INSTANCE = "mInstance";
    public static final String FILED_NAME_G_DEFAULT = "gDefault";
    public static final String FILED_NAME_I_ACTIVITY_MANAGER_SINGLETON = "IActivityManagerSingleton";
    public static final String FILED_NAME_I_ACTIVITY_TASK_MANAGER_SINGLETON = "IActivityTaskManagerSingleton";

    public static void hookAms() throws Exception
    {
        Class<?> iActivityManagerClazz;
        String className, filedName;
        if (Common.isSdkOverIncluding29())
        {
            className = CLASS_NAME_ACTIVITY_TASK_MANAGER;
            filedName = FILED_NAME_I_ACTIVITY_TASK_MANAGER_SINGLETON;
            iActivityManagerClazz = RefInvoke.getClass(CLASS_NAME_I_ACTIVITY_TASK_MANAGER);
        }
        else if (Common.isSdkOverIncluding26())
        {
            className = CLASS_NAME_ACTIVITY_MANAGER;
            filedName = FILED_NAME_I_ACTIVITY_MANAGER_SINGLETON;
            iActivityManagerClazz = RefInvoke.getClass(CLASS_NAME_I_ACTIVITY_MANAGER);
        }
        else
        {
            className = CLASS_NAME_ACTIVITY_MANAGER_NATIVE;
            filedName = FILED_NAME_G_DEFAULT;
            iActivityManagerClazz = RefInvoke.getClass(CLASS_NAME_I_ACTIVITY_MANAGER);
        }
        // Singleton<IActivityTaskManager> 对象
        Object singletonActivityManager = RefInvoke.getFieldObject(className, null, filedName);
        Field mInstanceField = RefInvoke.getField(CLASS_NAME_SINGLETON, FILED_NAME_M_INSTANCE);
        Object iActivityManager = mInstanceField.get(singletonActivityManager);
        if (iActivityManager == null)
        {
            Class<?> singleton = RefInvoke.getClass(CLASS_NAME_SINGLETON);
            Method method = singleton.getMethod("get");
            iActivityManager = method.invoke(singletonActivityManager);
        }

        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                                              new Class<?>[]{iActivityManagerClazz},
                                              new IActivityManagerProxy(iActivityManager));
        mInstanceField.set(singletonActivityManager, proxy);
    }
}
