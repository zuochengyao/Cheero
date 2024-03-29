package com.icheero.sdk.core.network.http.framework.apache;

import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;

public class HttpClientRequestFactory implements IHttpRequestFactory
{
    @Override
    public void setReadTimeout(int readTimeout)
    {
        HttpClientManager.getInstance().setSoTimeout(readTimeout);
    }

    @Override
    public void setConnectTimeout(int connectTimeout)
    {
        HttpClientManager.getInstance().setConnectTimeout(connectTimeout);
    }

    @Override
    public IHttpCall getHttpCall(HttpRequest request)
    {
        return new HttpClientCall(HttpClientManager.getInstance().newHttpClient(), request.getUrl(), request.getMethod(), request.getHeader(), request.getData(), request.getResponse());
    }
}
