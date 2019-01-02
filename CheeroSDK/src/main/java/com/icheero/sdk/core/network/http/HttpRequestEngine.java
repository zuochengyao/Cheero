package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpRequestFactory;
import com.icheero.sdk.util.Log;

import java.io.IOException;

public class HttpRequestEngine
{
    private static final Class TAG = HttpRequestEngine.class;
    private IHttpRequestFactory mHttpRequestFactory;

    private static volatile HttpRequestEngine mInstance;

    private HttpRequestEngine()
    {
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
        mHttpRequestFactory.setConnectTimeout(config.getConnectTimeout());
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
        try
        {
            HttpRequestProvider.getInstance().getHttpCall(request).enqueue();
        }
        catch (IOException e)
        {
            Log.e(TAG, "HttpRequest Enqueue Failed!");
        }
    }
}
