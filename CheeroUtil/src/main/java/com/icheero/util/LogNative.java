package com.icheero.util;

class LogNative
{
    static native void nativeSetTraceMode(int traceMode);

    static native void nativeTrace(String tag, String log, int prio);

    static
    {
        System.loadLibrary("cheero-1.0.0");
    }
}