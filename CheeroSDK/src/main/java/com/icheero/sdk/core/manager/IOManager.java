package com.icheero.sdk.core.manager;

public class IOManager
{
    private static final Class TAG = IOManager.class;
    private String s;

    private static volatile IOManager mInstance;

    private IOManager()
    {
        
    }

    public static IOManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (IOManager.class)
            {
                if (mInstance == null)
                    mInstance = new IOManager();
            }
        }
        return mInstance;
    }
}
