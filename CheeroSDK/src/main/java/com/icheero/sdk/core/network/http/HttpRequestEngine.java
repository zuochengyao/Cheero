package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.api.CheeroRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.util.Log;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpRequestEngine
{
    private static final Class TAG = HttpRequestEngine.class;
    private static final int REQUEST_SIZE_MAX = 60;

    private static final ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory()
    {
        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r)
        {
            return new Thread(r, "Http Thread #" + mInteger.getAndIncrement());
        }
    });

    private Deque<CheeroRequest> mRunningQueue;
    private Deque<CheeroRequest> mCacheQueue;

    private static volatile HttpRequestEngine mInstance;

    private HttpRequestEngine()
    {
        mRunningQueue = new ArrayDeque<>();
        mCacheQueue = new ArrayDeque<>();
    }

    public static HttpRequestEngine getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpRequestEngine.class)
            {
                if (mInstance == null)
                    mInstance = new HttpRequestEngine();
            }
        }
        return mInstance;
    }

    public void add(CheeroRequest cheeroRequest)
    {
        if (mRunningQueue.size() > REQUEST_SIZE_MAX)
            mCacheQueue.add(cheeroRequest);
        else
            doRequest(cheeroRequest);
    }

    void finish(CheeroRequest cheeroRequest)
    {
        mRunningQueue.remove(cheeroRequest);
        if (mRunningQueue.size() <= REQUEST_SIZE_MAX && mCacheQueue.size() > 0)
        {
            Iterator<CheeroRequest> iterator = mCacheQueue.iterator();
            while (iterator.hasNext())
            {
                CheeroRequest next = iterator.next();
                mRunningQueue.add(next);
                iterator.remove();
                doRequest(next);
            }
        }
    }

    private void doRequest(CheeroRequest cheeroRequest)
    {
        try
        {
            IHttpRequest httpRequest = HttpRequestProvider.getInstance().getHttpRequest(URI.create(cheeroRequest.getUrl()), cheeroRequest.getMethod(), cheeroRequest.getMediaType());
            mThreadPool.execute(new HttpRunnable(httpRequest, cheeroRequest));
        }
        catch (IOException e)
        {
            Log.e(TAG, "Http request enqueue failed!");
        }
    }
}
