package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.listener.IResponseListener;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

public abstract class AbstractHttpRequest implements IHttpRequest
{
    private static final String GZIP = "gzip";

    private HttpHeader mHttpHeader = new HttpHeader();
    private ZipOutputStream mZipOutStream;
    private boolean isExecuted = false;

    @Override
    public HttpHeader getHeaders()
    {
        return mHttpHeader;
    }

    @Override
    public OutputStream getBody()
    {
        OutputStream body = getBodyOutputStream();
        if (isGzip()) return getGzipOutputStream(body);
        return body;
    }

    @Override
    public IHttpResponse execute() throws IOException
    {
        synchronized (this)
        {
            if (isExecuted) throw new IllegalStateException("The Request Already Executed");
            isExecuted = true;
        }
        if (mZipOutStream != null) mZipOutStream.close();
        IHttpResponse response = execute(mHttpHeader);
        isExecuted = true;
        return response;
    }

    @Override
    public void enqueue(IResponseListener listener)
    {
        // TODO
        synchronized (this)
        {
            if (isExecuted) throw new IllegalStateException("The Request Already Executed");
            isExecuted = true;
        }
    }

    private boolean isGzip()
    {
        String contentEncoding = getHeaders().getContentEncoding();
        return GZIP.equals(contentEncoding);
    }

    private OutputStream getGzipOutputStream(OutputStream body)
    {
        if (this.mZipOutStream == null) this.mZipOutStream = new ZipOutputStream(body);
        return this.mZipOutStream;
    }

    protected abstract OutputStream getBodyOutputStream();

    protected abstract IHttpResponse execute(HttpHeader header) throws IOException;
}
