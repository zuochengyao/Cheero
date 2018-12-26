package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OriginHttpRequestFactory implements IHttpRequestFactory
{
    private HttpURLConnection mConnection;

    public OriginHttpRequestFactory()
    {
        // TODO : init mConnection
    }

    @Override
    public void setReadTimeout(int readTimeout)
    {
        mConnection.setReadTimeout(readTimeout * 1000);
    }

    @Override
    public void setConnectionTimeout(int connectionTimeout)
    {
        mConnection.setConnectTimeout(connectionTimeout * 1000);
    }

    @Override
    public IHttpCall getHttpCall(HttpRequest request)
    {
        try
        {
            mConnection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new OriginHttpCall(mConnection, request.getMethod(), request.getUrl());
    }
}
