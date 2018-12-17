package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.implement.AbstractHttpResponse;
import com.icheero.sdk.core.network.http.implement.HttpHeader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author 左程耀
 *
 * 传统HttpURLConnection网络请求框架
 */
public class OriginHttpResponse extends AbstractHttpResponse
{
    private HttpURLConnection mConnection;

    public OriginHttpResponse(HttpURLConnection connection)
    {
        this.mConnection = connection;
    }

    @Override
    protected InputStream getBodyStream() throws IOException
    {
        return mConnection.getInputStream();
    }

    @Override
    protected void closeBodyStream()
    {
        mConnection.disconnect();
    }

    @Override
    public HttpStatus getStatus()
    {
        try
        {
            return HttpStatus.getValue(mConnection.getResponseCode());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getStatusMessage()
    {
        try
        {
            return mConnection.getResponseMessage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpHeader getHeaders()
    {
        HttpHeader header = new HttpHeader();
        for (Map.Entry<String, List<String>> entry : mConnection.getHeaderFields().entrySet())
            header.set(entry.getKey(), entry.getValue().get(0));
        return header;
    }
}
