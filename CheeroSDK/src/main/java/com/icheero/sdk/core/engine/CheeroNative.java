package com.icheero.sdk.core.engine;

public class CheeroNative
{
    public static native void nativeSetTraceMode(int traceMode);

    public static native void nativeTrace(String tag, String log, int prio);

    static
    {
        System.loadLibrary("cheero-1.0.0");
    }
}