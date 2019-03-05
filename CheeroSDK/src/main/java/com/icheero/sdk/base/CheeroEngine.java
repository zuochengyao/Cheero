package com.icheero.sdk.base;

import android.app.Activity;

public class CheeroEngine
{
    private static final Class TAG = CheeroEngine.class;

    public static native void nativeHelloWorld();

    public static native void nativeCallJavaMethod(Activity activity);

    public static native void nativeCallJavaNonVirtualMethod();

    public static native void nativeGetSystemDateTime();

    public static native void nativeCppString();

    public static native void nativeCppArray();

    public static native void nativeSetTraceMode(int traceMode);

    public static native void nativeTrace(String tag, String log, int prio);

    public static native void nativeSetTraceFilePath(String filePath);

    static
    {
        System.loadLibrary("cheero-1.0.0");
    }
}
