package com.icheero.common.util;

import com.icheero.common.base.CheeroNative;

/**
 * Created by zuochengyao on 2018/3/1.
 */

public class Log
{
    private static final int ANDROID_LOG_INFO = 0;
    private static final int ANDROID_LOG_ERROR = 1;
    private static final int ANDROID_LOG_DEBUG = 2;
    private static final int ANDROID_LOG_WARN = 3;

    public static void i(Class<?> cls, String log)
    {
        CheeroNative.nativeTrace(cls.getName(), log, ANDROID_LOG_INFO);
    }

    public static void e(Class<?> cls, String log)
    {
        CheeroNative.nativeTrace(cls.getName(), log, ANDROID_LOG_ERROR);
    }

    public static void d(Class<?> cls, String log)
    {
        CheeroNative.nativeTrace(cls.getName(), log, ANDROID_LOG_DEBUG);
    }

    public static void w(Class<?> cls, String log)
    {
        CheeroNative.nativeTrace(cls.getName(), log, ANDROID_LOG_WARN);
    }
}
