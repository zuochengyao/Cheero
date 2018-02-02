package com.zcy.sdk.engine;

/**
 * Created by zuochengyao on 2018/2/2.
 */

public class JniNative
{
    public static native String helloWorld();

    public static native void updateFileContent(String filePath);

    static
    {
        System.loadLibrary("Zcy");
    }

}
