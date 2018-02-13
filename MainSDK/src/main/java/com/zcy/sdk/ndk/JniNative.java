package com.zcy.sdk.ndk;

/**
 * Created by zuochengyao on 2018/2/2.
 */

public class JniNative
{
    public static native void serviceSetTraceMode(int traceMode);

    public static native void serviceSizeOfDataType();

    static
    {
        System.loadLibrary("zcy-0.1.0");
    }

}
