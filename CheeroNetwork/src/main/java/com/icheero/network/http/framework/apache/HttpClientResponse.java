package com.icheero.network.http.framework.apache;

import com.icheero.network.http.encapsulation.HttpStatus;
import com.icheero.network.http.implement.AbstractHttpResponse;
import com.icheero.network.http.implement.HttpHeader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpClientResponse extends AbstractHttpResponse
{
    private HttpResponse mResponse;
    private HttpEntity mEntity;
    private InputStream mBodyStream;

    HttpClientResponse(HttpResponse response)
    {
        this.mResponse = response;
        this.mEntity = response.getEntity();
        try
        {
            this.mBodyStream = mEntity.getContent();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public long getContentLength()
    {
        return mEntity.getContentLength();
    }

    @Override
    public HttpStatus getStatus()
    {
        return HttpStatus.getValue(mResponse.getStatusLine().getStatusCode());
    }

    @Override
    public String getStatusMessage()
    {
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new InputStreamReader(mBodyStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            return sb.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeBodyStream();
        }
        return null;
    }

    @Override
    protected InputStream getBodyStream()
    {
        return mBodyStream;
    }

    @Override
    protected void closeBodyStream()
    {
        try
        {
            getBodyStream().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public HttpHeader getHeaders()
    {
        HttpHeader mHeader = new HttpHeader();
        for (Header header : mResponse.getAllHeaders())
            mHeader.put(header.getName(), header.getValue());
        return mHeader;
    }
}
