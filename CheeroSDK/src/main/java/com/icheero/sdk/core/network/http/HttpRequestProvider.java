package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.origin.OriginHttpRequestFactory;
import com.icheero.sdk.util.Common;

import java.io.IOException;
import java.net.URI;

public class HttpRequestProvider
{
    private static final String CLASSNAME_OKHTTP3_OKHTTPCLIENT = "okhttp3.OkHttpClient";

    private IHttpRequestFactory mHttpRequestFactory;

    private static volatile HttpRequestProvider mInstance;

    private HttpRequestProvider()
    {
        // 是否支持okhttp3
        boolean isOkHttpSupport = Common.isClassExist(CLASSNAME_OKHTTP3_OKHTTPCLIENT, HttpRequestProvider.class.getClassLoader());
        if (isOkHttpSupport)
            mHttpRequestFactory = new OkHttpRequestFactory();
        else
            mHttpRequestFactory = new OriginHttpRequestFactory();
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

    public IHttpRequest getHttpRequest(URI uri, HttpMethod method, String mediaType) throws IOException
    {
        return mHttpRequestFactory.createHttpRequest(uri, method, mediaType);
    }

    public IHttpRequestFactory getHttpRequestFactory()
    {
        return mHttpRequestFactory;
    }

    public void setHttpRequestFactory(IHttpRequestFactory httpRequestFactory)
    {
        this.mHttpRequestFactory = httpRequestFactory;
    }
}
