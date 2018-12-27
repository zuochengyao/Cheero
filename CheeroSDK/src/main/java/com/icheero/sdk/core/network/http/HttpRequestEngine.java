package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpCall;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpRequestFactory;
import com.icheero.sdk.util.Log;

import java.io.IOException;
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

    private Deque<HttpRequest> mRunningQueue;
    private Deque<HttpRequest> mCacheQueue;
    private IHttpRequestFactory mHttpRequestFactory;

    private static volatile HttpRequestEngine mInstance;

    private HttpRequestEngine()
    {
        mRunningQueue = new ArrayDeque<>();
        mCacheQueue = new ArrayDeque<>();
        mHttpRequestFactory = HttpRequestProvider.getInstance().getHttpRequestFactory();
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

    public void init(HttpConfig config)
    {
        mHttpRequestFactory.setConnectionTimeout(config.getConnectTimeout());
        mHttpRequestFactory.setReadTimeout(config.getReadTimeout());
        if (mHttpRequestFactory instanceof OkHttpRequestFactory)
        {
            ((OkHttpRequestFactory) mHttpRequestFactory).setWriteTimeout(config.getWriteTimeout());
            ((OkHttpRequestFactory) mHttpRequestFactory).setRetryOnConnectionFailure(config.isRetryOnConnectionFailure());
        }
    }

    /**
     * 异步请求：将请求添加到请求队列中，通过回调获取结果
     */
    public void enqueue(HttpRequest request)
    {
//        if (mRunningQueue.size() > REQUEST_SIZE_MAX)
//            mCacheQueue.add(request);
//        else
//            doRequest(request);
        // TODO --------------------------------------------------
        try
        {
             OkHttpCall call = (OkHttpCall) HttpRequestProvider.getInstance().getHttpCall(request);
             if (call != null)
                 call.enqueue(request.getResponse());
        }
        catch (IOException e)
        {
            Log.e(TAG, "HttpRequest Enqueue Failed!");
        }
    }

    /**
     * 异步请求完成后调用
     */
    void finish(HttpRequest request)
    {
        mRunningQueue.remove(request);
        if (mRunningQueue.size() <= REQUEST_SIZE_MAX && mCacheQueue.size() > 0)
        {
            Iterator<HttpRequest> iterator = mCacheQueue.iterator();
            while (iterator.hasNext())
            {
                HttpRequest next = iterator.next();
                mRunningQueue.add(next);
                iterator.remove();
                doRequest(next);
            }
        }
    }

    private void doRequest(HttpRequest request)
    {
        IHttpCall httpCall = HttpRequestProvider.getInstance().getHttpCall(request);
        if (httpCall != null)
        {
            mRunningQueue.add(request);
            mThreadPool.execute(new HttpRunnable(httpCall, request));
        }
    }
}
