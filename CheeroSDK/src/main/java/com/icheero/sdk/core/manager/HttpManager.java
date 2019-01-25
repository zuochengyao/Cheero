package com.icheero.sdk.core.manager;

import com.icheero.util.Log;
import com.icheero.network.http.HttpConfig;
import com.icheero.network.http.HttpRequest;
import com.icheero.network.http.HttpRequestProvider;
import com.icheero.network.http.encapsulation.IHttpRequestFactory;
import com.icheero.network.http.framework.okhttp.OkHttpRequestFactory;

import java.io.IOException;

public class HttpManager
{
    private static final Class TAG = HttpManager.class;

    private HttpRequestProvider mHttpRequestProvider;
    private IHttpRequestFactory mHttpRequestFactory;
    private static volatile HttpManager mInstance;

    private HttpManager()
    {
        mHttpRequestProvider = HttpRequestProvider.getInstance();
    }

    public static HttpManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpManager.class)
            {
                if (mInstance == null)
                    mInstance = new HttpManager();
            }
        }
        return mInstance;
    }

    public void init(HttpConfig config)
    {
        mHttpRequestFactory = mHttpRequestProvider.getHttpRequestFactory(config.getHttpClassName());
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
