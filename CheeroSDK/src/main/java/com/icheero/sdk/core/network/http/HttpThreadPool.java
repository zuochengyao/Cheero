package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.implement.AbstractAsyncHttpCall;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpThreadPool
{
    private static final int REQUEST_SIZE_MAX = 64;

    private Deque<AbstractAsyncHttpCall> mRunningQueue;
    private Deque<AbstractAsyncHttpCall> mCacheQueue;
    private static volatile HttpThreadPool mInstance;

    private static final ExecutorService mThreadPool = Executors.newCachedThreadPool(new ThreadFactory()
    {
        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r)
        {
            return new Thread(r, "Http Thread #" + mInteger.getAndIncrement());
        }
    });

    private HttpThreadPool()
    {
        mRunningQueue = new ArrayDeque<>();
        mCacheQueue = new ArrayDeque<>();
    }

    public static HttpThreadPool getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpThreadPool.class)
            {
                if (mInstance == null)
                    mInstance = new HttpThreadPool();
            }
        }
        return mInstance;
    }

    public void enqueue(AbstractAsyncHttpCall httpCall)
    {
        if (mRunningQueue.size() > REQUEST_SIZE_MAX)
            mCacheQueue.add(httpCall);
        else
            doRequest(httpCall);
    }

    /**
     * 异步请求完成后调用
     */
    void finish(AbstractAsyncHttpCall httpCall)
    {
        mRunningQueue.remove(httpCall);
        if (mRunningQueue.size() <= REQUEST_SIZE_MAX && mCacheQueue.size() > 0)
        {
            Iterator<AbstractAsyncHttpCall> iterator = mCacheQueue.iterator();
            while (iterator.hasNext())
            {
                AbstractAsyncHttpCall next = iterator.next();
                mRunningQueue.add(next);
                iterator.remove();
                doRequest(next);
            }
        }
    }

    private void doRequest(AbstractAsyncHttpCall httpCall)
    {
        if (httpCall != null)
        {
            mRunningQueue.add(httpCall);
            mThreadPool.execute(new HttpRunnable(httpCall, httpCall.getCallback()));
        }
    }
}
