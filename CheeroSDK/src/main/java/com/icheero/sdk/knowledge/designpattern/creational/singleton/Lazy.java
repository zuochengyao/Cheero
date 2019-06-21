package com.icheero.sdk.knowledge.designpattern.creational.singleton;

public class Lazy
{
    private static volatile Lazy mInstance;

    private Lazy()
    { }

    public static Lazy getInstance()
    {
        if (mInstance == null)
        {
            synchronized (Lazy.class)
            {
                if (mInstance == null)
                    mInstance = new Lazy();
            }
        }
        return mInstance;
    }
}
