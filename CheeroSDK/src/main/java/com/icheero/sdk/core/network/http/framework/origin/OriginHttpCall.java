package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.http.implement.BufferHttpCall;
import com.icheero.sdk.core.network.http.implement.HttpHeader;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class OriginHttpCall extends BufferHttpCall
{
    private HttpURLConnection mConnection;
    private HttpMethod mMethod;
    private String mUrl;

    public OriginHttpCall(HttpURLConnection connection, HttpMethod method, String url)
    {
        this.mConnection = connection;
        this.mMethod = method;
        this.mUrl = url;
    }

    @Override
    protected IHttpResponse execute(HttpHeader header, byte[] data) throws IOException
    {
        for (Map.Entry<String, String> entry : header.entrySet())
            mConnection.addRequestProperty(entry.getKey(), entry.getValue());
        mConnection.setDoOutput(true);
        mConnection.setDoInput(true);
        mConnection.setRequestMethod(mMethod.name());
        mConnection.connect();
        if (data != null && data.length > 0)
        {
            OutputStream out = mConnection.getOutputStream();
            out.write(data, 0, data.length);
            out.close();
        }
        return new OriginHttpResponse(mConnection);
    }

    @Override
    protected void enqueue(HttpHeader header, byte[] data)
    {
        // TODO
    }

    @Override
    public HttpMethod getMethod()
    {
        return mMethod;
    }

    @Override
    public URI getUri()
    {
        return URI.create(mUrl);
    }
}
