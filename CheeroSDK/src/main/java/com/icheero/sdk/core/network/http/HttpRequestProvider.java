package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.apache.HttpClientRequestFactory;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.origin.OriginHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.volley.VolleyRequestFactory;
import com.icheero.sdk.util.Common;


public class HttpRequestProvider
{
    private static final Class TAG = HttpRequestProvider.class;

    private IHttpRequestFactory mHttpRequestFactory;

    private static volatile HttpRequestProvider mInstance;

    private HttpRequestProvider()
    {
    }

    public static HttpRequestProvider getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpRequestProvider.class)
            {
                if (mInstance == null)
                    mInstance = new HttpRequestProvider();
            }
        }
        return mInstance;
    }

    public IHttpCall getHttpCall(HttpRequest request)
    {
        // URI.create(request.getUrl()), request.getMethod(), request.getMediaType(), request.getResponse()
        return mHttpRequestFactory.getHttpCall(request);
    }

    public IHttpRequestFactory getHttpRequestFactory(String className)
    {
        // 是否支持okhttp3
        if (HttpConfig.CLASSNAME_OKHTTP.equals(className) && Common.isClassExist(HttpConfig.CLASSNAME_OKHTTP))
            mHttpRequestFactory = new OkHttpRequestFactory();
        // 是否支持volley
        else if (HttpConfig.CLASSNAME_VOLLEY.equals(className) && Common.isClassExist(HttpConfig.CLASSNAME_VOLLEY))
            mHttpRequestFactory = new VolleyRequestFactory();
        // 是否支持HttpClient
        else if (HttpConfig.CLASSNAME_APACHE.equals(className) && Common.isClassExist(HttpConfig.CLASSNAME_APACHE))
            mHttpRequestFactory = new HttpClientRequestFactory();
        else // 若都不支持，则使用android自带的
            mHttpRequestFactory = new OriginHttpRequestFactory();
        return mHttpRequestFactory;
    }
}
