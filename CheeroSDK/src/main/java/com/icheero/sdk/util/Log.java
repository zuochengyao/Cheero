package com.icheero.sdk.util;

import com.icheero.sdk.base.CheeroNative;

/**
 * Created by zuochengyao on 2018/3/1.
 *
 * 日志输出管理类
 */
@SuppressWarnings("unused")
public class Log
{
    public static final int TRACE_MODE_ON_SCREEN = 0;
    public static final int TRACE_MODE_ON_FILE = 1;
    public static final int TRACE_MODE_OFF = 2;

    private static final int ANDROID_LOG_INFO = 0;
    private static final int ANDROID_LOG_ERROR = 1;
    private static final int ANDROID_LOG_DEBUG = 2;
    private static final int ANDROID_LOG_WARN = 3;

    public static void i(Class<?> cls, String log)
    {
        trace(cls, log, ANDROID_LOG_INFO);
    }

    public static void e(Class<?> cls, String log)
    {
        trace(cls, log, ANDROID_LOG_ERROR);
    }

    public static void d(Class<?> cls, String log)
    {
        trace(cls, log, ANDROID_LOG_DEBUG);
    }

    public static void w(Class<?> cls, String log)
    {
        trace(cls, log, ANDROID_LOG_WARN);
    }

    public static void traceMode(int mode)
    {
        CheeroNative.nativeSetTraceMode(mode);
    }

    private static void trace(Class<?> cls, String log, int prio)
    {
        log += "\n";
        CheeroNative.nativeTrace(cls.getName(), log, prio);
    }

    public static void print()
    {
        String error = null;
        android.util.Log.e("Cheero", error);
    }
}
