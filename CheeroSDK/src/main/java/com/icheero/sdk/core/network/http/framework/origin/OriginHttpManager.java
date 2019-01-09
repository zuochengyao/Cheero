package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.HttpSecure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.HttpsURLConnection;

public class OriginHttpManager
{
    private static final Class TAG = OriginHttpManager.class;
    private static final int REQUEST_SIZE_MAX = 64;

    private static final ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory()
    {
        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r)
        {
            return new Thread(r, "Http Thread #" + mInteger.getAndIncrement());
        }
    });

    private Deque<OriginHttpCall> mRunningQueue;
    private Deque<OriginHttpCall> mCacheQueue;
    private static volatile OriginHttpManager mInstance;

    private OriginHttpManager()
    {
        mRunningQueue = new ArrayDeque<>();
        mCacheQueue = new ArrayDeque<>();
        ignoreSsl();
    }

    public static OriginHttpManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OriginHttpManager.class)
            {
                if (mInstance == null)
                    mInstance = new OriginHttpManager();
            }
        }
        return mInstance;
    }

    void enqueue(OriginHttpCall httpCall)
    {
        if (mRunningQueue.size() > REQUEST_SIZE_MAX)
        {
            mCacheQueue.add(httpCall);
        }
        else
        {
            doRequest(httpCall);
        }
    }

    /**
     * 异步请求完成后调用
     */
    void finish(OriginHttpCall httpCall)
    {
        mRunningQueue.remove(httpCall);
        if (mRunningQueue.size() <= REQUEST_SIZE_MAX && mCacheQueue.size() > 0)
        {
            Iterator<OriginHttpCall> iterator = mCacheQueue.iterator();
            while (iterator.hasNext())
            {
                OriginHttpCall next = iterator.next();
                mRunningQueue.add(next);
                iterator.remove();
                doRequest(next);
            }
        }
    }

    private void doRequest(OriginHttpCall httpCall)
    {
        if (httpCall != null)
        {
            mRunningQueue.add(httpCall);
            mThreadPool.execute(new OriginHttpRunnable(httpCall, httpCall.getListener()));
        }
    }

    /**
     * 忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     */
    private void ignoreSsl()
    {
        HttpsURLConnection.setDefaultSSLSocketFactory(HttpSecure.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(HttpSecure.hv);
    }
}
