package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class BufferHttpCall extends AbstractHttpCall
{
    private ByteArrayOutputStream mByteArray = new ByteArrayOutputStream();

    protected OutputStream getBodyOutputStream()
    {
        return mByteArray;
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

    protected abstract IHttpResponse execute(HttpHeader header, byte[] data) throws IOException;

    protected abstract void enqueue(HttpHeader header, byte[] data);
}
