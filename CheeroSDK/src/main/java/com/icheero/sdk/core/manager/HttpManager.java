package com.icheero.sdk.core.manager;

import com.icheero.sdk.core.network.http.HttpConfig;
import com.icheero.sdk.core.network.http.HttpRequestProvider;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpRequestFactory;

import java.io.IOException;
import java.net.URI;

public class HttpManager
{
    private IHttpRequestFactory mHttpRequestFactory;
    private static volatile HttpManager mInstance;

    private HttpManager()
    {
        mHttpRequestFactory = HttpRequestProvider.getInstance().getHttpRequestFactory();
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
        mHttpRequestFactory.setConnectionTimeout(config.getConnectTimeout());
        mHttpRequestFactory.setReadTimeout(config.getReadTimeout());
        if (mHttpRequestFactory instanceof OkHttpRequestFactory)
        {
            ((OkHttpRequestFactory) mHttpRequestFactory).setWriteTimeout(config.getWriteTimeout());
            ((OkHttpRequestFactory) mHttpRequestFactory).setRetryOnConnectionFailure(config.isRetryOnConnectionFailure());
        }
    }

    public IHttpRequest getHttpRequest(URI uri, HttpMethod method, String mediaType)
    {
        try
        {
            return HttpRequestProvider.getInstance().getHttpRequest(uri, method, mediaType);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
