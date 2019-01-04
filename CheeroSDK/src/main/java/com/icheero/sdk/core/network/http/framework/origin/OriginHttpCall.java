package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.http.implement.AbstractHttpCall;
import com.icheero.sdk.core.network.http.implement.HttpHeader;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class OriginHttpCall extends AbstractHttpCall
{
    private HttpURLConnection mConnection;
    private String mUrl;
    private HttpMethod mMethod;
    private HttpHeader mHeader;
    private AbstractHttpEntity mData;
    private HttpResponse mListener;

    OriginHttpCall(HttpURLConnection connection, String url, HttpMethod method, HttpHeader header, AbstractHttpEntity data, HttpResponse listener)
    {
        this.mConnection = connection;
        this.mUrl = url;
        this.mMethod = method;
        this.mHeader = header;
        this.mData = data;
        this.mListener = listener;
    }

    public HttpResponse getListener()
    {
        return mListener;
    }

    @Override
    public IHttpResponse execute() throws IOException
    {
        checkExecuted();
        for (Map.Entry<String, String> entry : mHeader.entrySet())
            mConnection.setRequestProperty(entry.getKey(), entry.getValue());
        mConnection.setDoOutput(true);
        mConnection.setDoInput(true);
        mConnection.setRequestMethod(mMethod.name());
        mConnection.connect();
        if (mData != null && mData.getBytes().length > 0)
        {
            OutputStream out = mConnection.getOutputStream();
            out.write(mData.getBytes());
            out.close();
        }
        return new OriginHttpResponse(mConnection);
    }

    @Override
    public void enqueue()
    {
        OriginHttpManager.getInstance().enqueue(this);
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

    @Override
    protected HttpHeader getHeaders()
    {
        return null;
    }

    private void doExecute()
    {

    }
}
