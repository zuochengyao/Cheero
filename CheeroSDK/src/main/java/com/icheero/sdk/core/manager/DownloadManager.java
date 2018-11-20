package com.icheero.sdk.core.manager;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadManager
{ 
    private final static int THREAD_COUNT_CORE = 2;
    private final static int THREAD_COUNT_MAX = 2;
    private final static int THREAD_ALIVE_TIME = 60;

    private ThreadPoolExecutor mThreadPool;
    private static volatile DownloadManager mInstance;

    private DownloadManager()
    {
        mThreadPool = new ThreadPoolExecutor(THREAD_COUNT_CORE, THREAD_COUNT_MAX, THREAD_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory()
        {
            private AtomicInteger mInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "Download Thread #" + mInteger.getAndIncrement());
            }
        });
    }

    public static DownloadManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (DownloadManager.class)
            {
                if (mInstance == null) mInstance = new DownloadManager();
            }
        }
        return mInstance;
    }
}
