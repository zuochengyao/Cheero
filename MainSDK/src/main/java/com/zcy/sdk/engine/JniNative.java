package com.zcy.sdk.engine;

/**
 * Created by zuochengyao on 2018/2/2.
 */

public class JniNative
{
    public native void helloWorld();

    public native int add(int a, int b);

    public native String sayHello();

    static
    {
        System.loadLibrary("zcy-0.1.0");
    }

}
