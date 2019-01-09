package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class OriginHttpRequestFactory implements IHttpRequestFactory
{
    private int mReadTimeout;
    private int mConnectTimeout;

    @Override
    public void setReadTimeout(int readTimeout)
    {
        mReadTimeout = readTimeout * 1000;
    }

    @Override
    public void setConnectTimeout(int connectTimeout)
    {
        mConnectTimeout = connectTimeout * 1000;
    }

    @Override
    public IHttpCall getHttpCall(HttpRequest request)
    {
        HttpURLConnection connection = null;
        try
        {
            if (request.getUrl().startsWith("https://"))
                connection = (HttpsURLConnection) new URL(request.getUrl()).openConnection();
            else
                connection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
            connection.setReadTimeout(mReadTimeout);
            connection.setConnectTimeout(mConnectTimeout);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new OriginHttpCall(connection, request.getUrl(), request.getMethod(), request.getHeader(), request.getData(), request.getResponse());
    }
}
