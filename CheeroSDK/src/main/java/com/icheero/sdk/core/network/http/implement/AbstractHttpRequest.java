package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

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
        if (isGzip())
            return getGzipOutputStream(body);
        return null;
    }

    @Override
    public IHttpResponse execute() throws IOException
    {
        if (mZipOutStream != null)
            mZipOutStream.close();
        IHttpResponse response = executeRequest(mHttpHeader);
        isExecuted = true;
        return response;
    }

    private boolean isGzip()
    {
        String contentEncoding = getHeaders().getContentEncoding();
        return GZIP.equals(contentEncoding);
    }

    private OutputStream getGzipOutputStream(OutputStream body)
    {
        if (this.mZipOutStream == null)
            this.mZipOutStream = new ZipOutputStream(body);
        return this.mZipOutStream;
    }

    protected abstract OutputStream getBodyOutputStream();

    protected abstract IHttpResponse executeRequest(HttpHeader header) throws IOException;
}