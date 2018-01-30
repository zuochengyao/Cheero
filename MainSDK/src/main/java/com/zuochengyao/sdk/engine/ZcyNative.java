package com.zuochengyao.sdk.engine;

/**
 * Created by zuochengyao on 2018/1/29.
 */

public class ZcyNative
{
    static
    {
        System.loadLibrary("zcy");
    }
    public static native String helloWorld();

    public static native void updateFileContent(String filePath);
}
