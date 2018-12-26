package com.icheero.sdk.core.network.http.framework.volley;

import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;

// TODO
public class VolleyRequestFactory implements IHttpRequestFactory
{
    @Override
    public void setReadTimeout(int readTimeout)
    {

    }

    @Override
    public void setConnectionTimeout(int connectionTimeout)
    {

    }

    @Override
    public IHttpCall getHttpCall(HttpRequest request)
    {
        return null;
    }
}
