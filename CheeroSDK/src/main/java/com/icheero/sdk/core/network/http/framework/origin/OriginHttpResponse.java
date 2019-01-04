package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.implement.AbstractHttpResponse;
import com.icheero.sdk.core.network.http.implement.HttpHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    OriginHttpResponse(HttpURLConnection connection)
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
    public long getContentLength()
    {
        return mConnection.getContentLength();
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
            if (getStatus().isSuccess())
                return mConnection.getResponseMessage();
            else
            {
                StringBuilder error = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mConnection.getErrorStream(), "UTF-8"));
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    error.append(line);
                bufferedReader.close();
                return error.toString();
            }
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
            header.put(entry.getKey(), entry.getValue().get(0));
        return header;
    }
}
