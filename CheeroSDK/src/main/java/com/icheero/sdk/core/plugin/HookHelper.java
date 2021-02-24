package com.icheero.sdk.core.plugin;

import android.os.Handler;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.RefUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookHelper
{
    public static final String TARGET_INTENT = "target_intent";

    public static void hookActivityManagerService() throws Exception
    {
        Class<?> iActivityManagerClazz;
        String className, filedName;
        if (Common.isSdkOverIncluding29())
        {
            className = RefUtils.CLASS_ACTIVITY_TASK_MANAGER;
            filedName = RefUtils.FILED_I_ACTIVITY_TASK_MANAGER_SINGLETON;
            iActivityManagerClazz = RefUtils.getClass(RefUtils.CLASS_I_ACTIVITY_TASK_MANAGER);
        }
        else if (Common.isSdkOverIncluding26())
        {
            className = RefUtils.CLASS_ACTIVITY_MANAGER;
            filedName = RefUtils.FILED_I_ACTIVITY_MANAGER_SINGLETON;
            iActivityManagerClazz = RefUtils.getClass(RefUtils.CLASS_I_ACTIVITY_MANAGER);
        }
        else
        {
            className = RefUtils.CLASS_ACTIVITY_MANAGER_NATIVE;
            filedName = RefUtils.FILED_G_DEFAULT;
            iActivityManagerClazz = RefUtils.getClass(RefUtils.CLASS_I_ACTIVITY_MANAGER);
        }
        // Singleton<IActivityTaskManager> 对象
        Object singletonActivityManager = RefUtils.getStaticDeclaredFieldValue(className, filedName);
        Field mInstanceField = RefUtils.getDeclaredField(RefUtils.CLASS_SINGLETON, RefUtils.FILED_M_INSTANCE);
        Object iActivityManager = mInstanceField.get(singletonActivityManager);
        if (iActivityManager == null)
        {
            Class<?> singleton = RefUtils.getClass(RefUtils.CLASS_SINGLETON);
            Method method = singleton.getMethod(RefUtils.METHOD_GET);
            iActivityManager = method.invoke(singletonActivityManager);
        }

        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                                              new Class<?>[]{iActivityManagerClazz},
                                              new IActivityManagerProxy(iActivityManager));
        mInstanceField.set(singletonActivityManager, proxy);
    }

    public static void hookActivityThreadHandler()
    {
        try
        {
            // 获取 ActivityThread 实例
            Object currentActivityThread = RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD, null, RefUtils.FILED_S_CURRENT_ACTIVITY_THREAD);
            // 获取 ActivityThread 中 mH 属性
            Handler mH = (Handler) RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD, RefUtils.FILED_M_H, currentActivityThread);
            RefUtils.setFieldObject(Handler.class, RefUtils.FILED_M_CALLBACK, mH, new HookHandlerCallback(mH));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
