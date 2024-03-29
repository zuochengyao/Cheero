package com.icheero.sdk.base;

public class CheeroNative
{
    private static final Class TAG = CheeroNative.class;
    String i = "";

    public static native void nativeHelloWorld();

    public static native void nativeCallJavaMethod(Object object);

    public static native void nativeCallJavaNonVirtualMethod(Object object);

    public static native void nativeGetSystemDateTime();

    public static native void nativeCppString(Object object);

    public static native void nativeCppArray(Object object);

    public static native void nativeSetTraceMode(int traceMode);

    public static native void nativeTrace(String tag, String log, int prio);

    public static native void nativeSetTraceFilePath(String filePath);

    public static native void nativeIsOwnApp();

    static
    {
        System.loadLibrary("cheero-1.0.0");
    }
}
