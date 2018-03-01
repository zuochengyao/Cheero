package com.zcy.sdk.util;

import com.zcy.sdk.ndk.JniNative;

/**
 * Created by zuochengyao on 2018/3/1.
 */

public class Log
{
    static final int ANDROID_LOG_INFO = 0;
    static final int ANDROID_LOG_ERROR = 1;
    static final int ANDROID_LOG_DEBUG = 2;
    static final int ANDROID_LOG_WARN = 3;

    public static void i(Class<?> cls, String log)
    {
        JniNative.serviceTrace(cls.getName(), log, ANDROID_LOG_INFO);
    }

    public static void e(Class<?> cls, String log)
    {
        JniNative.serviceTrace(cls.getName(), log, ANDROID_LOG_ERROR);
    }

    public static void d(Class<?> cls, String log)
    {
        JniNative.serviceTrace(cls.getName(), log, ANDROID_LOG_DEBUG);
    }

    public static void w(Class<?> cls, String log)
    {
        JniNative.serviceTrace(cls.getName(), log, ANDROID_LOG_WARN);
    }
}
