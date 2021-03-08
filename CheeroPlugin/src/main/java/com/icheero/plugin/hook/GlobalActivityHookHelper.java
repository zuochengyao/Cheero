package com.icheero.plugin.hook;

import android.os.Handler;

import com.icheero.plugin.hook.proxy.HCallbackProxy;
import com.icheero.plugin.hook.proxy.IActivityManagerProxy;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.RefUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GlobalActivityHookHelper
{
    public static final String TARGET_INTENT = "target_intent";

    public static void hook() throws Exception
    {
        hookActivityManagerService();
        hookActivityThreadHandler();
    }

    private static void hookActivityManagerService() throws Exception
    {
        String amClassName, filedName, iAmClassName;
        if (Common.isSdkOverIncluding29())
        {
            amClassName = RefUtils.CLASS_ACTIVITY_TASK_MANAGER;
            filedName = RefUtils.FILED_I_ACTIVITY_TASK_MANAGER_SINGLETON;
            iAmClassName = RefUtils.CLASS_I_ACTIVITY_TASK_MANAGER;
        }
        else if (Common.isSdkOverIncluding26())
        {
            amClassName = RefUtils.CLASS_ACTIVITY_MANAGER;
            filedName = RefUtils.FILED_I_ACTIVITY_MANAGER_SINGLETON;
            iAmClassName = RefUtils.CLASS_I_ACTIVITY_MANAGER;
        }
        else
        {
            amClassName = RefUtils.CLASS_ACTIVITY_MANAGER_NATIVE;
            filedName = RefUtils.FILED_G_DEFAULT;
            iAmClassName = RefUtils.CLASS_I_ACTIVITY_MANAGER;
        }
        // Singleton<IActivityTaskManager> 对象
        Object singletonActivityManager = RefUtils.getStaticDeclaredFieldValue(amClassName,
                filedName);
        Field mInstanceField = RefUtils.getDeclaredField(RefUtils.CLASS_SINGLETON,
                RefUtils.FILED_M_INSTANCE);
        Object iActivityManager = mInstanceField.get(singletonActivityManager);
        if (iActivityManager == null)
        {
            Class<?> singleton = RefUtils.getClass(RefUtils.CLASS_SINGLETON);
            Method method = singleton.getMethod(RefUtils.METHOD_GET);
            iActivityManager = method.invoke(singletonActivityManager);
        }
        Class<?> iActivityManagerClazz = RefUtils.getClass(iAmClassName);
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {iActivityManagerClazz},
                new IActivityManagerProxy(iActivityManager));
        mInstanceField.set(singletonActivityManager, proxy);
    }

    private static void hookActivityThreadHandler() throws Exception
    {
        // 获取 ActivityThread 实例
        Object currentActivityThread = RefUtils.getObjectDeclaredFieldValue(
                RefUtils.CLASS_ACTIVITY_THREAD, RefUtils.FILED_S_CURRENT_ACTIVITY_THREAD, null);
        // 获取 ActivityThread 中 mH 属性
        Handler mH = (Handler) RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD,
                RefUtils.FILED_M_H, currentActivityThread);
        Handler.Callback hCallback;
        if (Common.isSdkOverIncluding28())
        {
            hCallback = new HCallbackProxy(mH);
        }
        else
        {
            hCallback = new HCallbackProxy(mH);
        }
        RefUtils.setFieldObject(Handler.class, RefUtils.FILED_M_CALLBACK, mH, hCallback);
    }
}
