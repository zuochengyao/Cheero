package com.icheero.sdk.util;

import com.icheero.sdk.base.CheeroNative;

/**
 * Created by zuochengyao on 2018/3/1.
 * 日志输出管理类
 */
@SuppressWarnings("unused")
public class Log
{
    private static final Class<?> TAG = Log.class;

    public static final int TRACE_MODE_ON_SCREEN = 0;
    public static final int TRACE_MODE_ON_FILE = 1;
    public static final int TRACE_MODE_OFF = 2;

    private static final int ANDROID_LOG_INFO = 0;
    private static final int ANDROID_LOG_ERROR = 1;
    private static final int ANDROID_LOG_DEBUG = 2;
    private static final int ANDROID_LOG_WARN = 3;

    private static int mTraceMode = TRACE_MODE_OFF;

    public static void i(Class<?> cls, String... logs)
    {
        for (String log : logs)
            trace(cls, log, ANDROID_LOG_INFO);
    }

    public static void e(Class<?> cls, String... logs)
    {
        for (String log : logs)
            trace(cls, log, ANDROID_LOG_ERROR);
    }

    public static void d(Class<?> cls, String... logs)
    {
        for (String log : logs)
            trace(cls, log, ANDROID_LOG_DEBUG);
    }

    public static void w(Class<?> cls, String... logs)
    {
        for (String log : logs)
            trace(cls, log, ANDROID_LOG_WARN);
    }

    public static void traceMode(int mode)
    {
        mTraceMode = mode;
        CheeroNative.nativeSetTraceMode(mode);
    }

    public static void traceFilePath(String filePath)
    {
        CheeroNative.nativeSetTraceFilePath(filePath);
    }

    private static String getFunctionName()
    {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null)
            return null;
        for (StackTraceElement st : sts)
        {
            if (st.isNativeMethod())
                continue;
            if (st.getClassName().equals(Thread.class.getName()))
                continue;
            if (st.getClassName().equals(TAG.getName()))
                continue;
            return st.getFileName() + "[Line: " + st.getLineNumber() + "] ";
        }
        return null;
    }

    private static void trace(Class<?> cls, String log, int prio)
    {
        log += "\n";
        CheeroNative.nativeTrace(mTraceMode == TRACE_MODE_ON_SCREEN ? cls.getSimpleName() : cls.getName(), log, prio);
    }

    public static void print()
    {
        String error = "FixBug";
        android.util.Log.e("Cheero", error);
    }
}
