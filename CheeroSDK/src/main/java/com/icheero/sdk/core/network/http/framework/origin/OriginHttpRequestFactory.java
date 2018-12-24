package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

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
    public IHttpRequest createHttpRequest(URI uri, HttpMethod method, String mediaType) throws IOException
    {
        mConnection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(mConnection, method, uri.toString());
    }
}
