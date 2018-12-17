package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpRequestFactory;
import com.icheero.sdk.core.network.http.framework.origin.OriginHttpRequestFactory;
import com.icheero.sdk.util.Common;

import java.io.IOException;
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
        else
            mHttpRequestFactory = new OriginHttpRequestFactory();
    }

    public IHttpRequest getHttpRequest(URI uri, HttpMethod method, String mimeType) throws IOException
    {
        return mHttpRequestFactory.createHttpRequest(uri, method, mimeType);
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
