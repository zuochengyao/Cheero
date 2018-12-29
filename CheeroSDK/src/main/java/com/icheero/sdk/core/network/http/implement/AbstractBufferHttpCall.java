package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

// TODO
public abstract class AbstractBufferHttpCall extends AbstractHttpCall
{
    private ZipOutputStream mZipOutStream;
    private ByteArrayOutputStream mByteArray = new ByteArrayOutputStream();

    public OutputStream getBody()
    {
        OutputStream body = getBodyOutputStream();
        if (isGzip())
            return getGzipOutputStream(body);
        return body;
    }

    protected OutputStream getBodyOutputStream()
    {
        return mByteArray;
    }

    @Override
    protected HttpHeader getHeaders()
    {
        return null;
    }

    @Override
    protected IHttpResponse execute(HttpHeader header) throws IOException
    {
        byte[] data = mByteArray.toByteArray();
        return execute(header, data);
    }

    @Override
    public void enqueue(HttpHeader header)
    {
        byte[] data = mByteArray.toByteArray();
        enqueue(header, data);
    }

    @Override
    protected void checkExecuted() throws IOException
    {
        super.checkExecuted();
        if (mZipOutStream != null)
            mZipOutStream.close();
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

    protected abstract IHttpResponse execute(HttpHeader header, byte[] data) throws IOException;

    protected abstract void enqueue(HttpHeader header, byte[] data);
}
