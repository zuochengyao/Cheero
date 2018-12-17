package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.framework.okhttp.OkHttpRequestFactory;
import com.icheero.sdk.util.Common;

import java.net.URI;

public class HttpRequestProvider
{
    private static final String CLASSNAME_OKHTTP3_OKHTTPCLIENT = "okhttp3.OkHttpClient";

    private IHttpRequestFactory mHttpRequestFactory;

    private boolean isOkHttpSupport;

    public HttpRequestProvider()
    {
        // 是否支持okhttp3
        isOkHttpSupport = Common.isClassExist(CLASSNAME_OKHTTP3_OKHTTPCLIENT, HttpRequestProvider.class.getClassLoader());
        if (isOkHttpSupport)
            mHttpRequestFactory = new OkHttpRequestFactory();
    }

    public IHttpRequest getHttpRequest(URI uri, IHttpRequest.HttpMethod method)
    {
        return mHttpRequestFactory.createHttpRequest(uri, method);
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
