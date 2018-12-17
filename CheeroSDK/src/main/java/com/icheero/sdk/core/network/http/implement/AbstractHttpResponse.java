package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public abstract class AbstractHttpResponse implements IHttpResponse
{
    private static final String GZIP = "gzip";

    private InputStream mGzipInStream;

    @Override
    public InputStream getBody() throws IOException
    {
        InputStream body = getBodyStream();
        return isGzip() ? getBodyGzip(body) : body;
    }

    @Override
    public void close() throws IOException
    {
        if (mGzipInStream != null)
            mGzipInStream.close();
        closeBodyStream();
    }

    private boolean isGzip()
    {
        String contentEncoding = getHeaders().getContentEncoding();
        return GZIP.equals(contentEncoding);
    }

    private InputStream getBodyGzip(InputStream body) throws IOException
    {
        if (this.mGzipInStream == null)
            this.mGzipInStream = new GZIPInputStream(body);
        return this.mGzipInStream;
    }

    protected abstract InputStream getBodyStream();

    protected abstract void closeBodyStream();
}
