package com.icheero.network.http.framework.volley;

import com.icheero.network.http.HttpRequest;
import com.icheero.network.http.encapsulation.IHttpCall;
import com.icheero.network.http.encapsulation.IHttpRequestFactory;

// TODO
public class VolleyRequestFactory implements IHttpRequestFactory
{
    @Override
    public void setReadTimeout(int readTimeout)
    {

    }

    @Override
    public void setConnectTimeout(int connectTimeout)
    {

    }

    @Override
    public IHttpCall getHttpCall(HttpRequest request)
    {
        return null;
    }
}
