package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class BufferHttpRequest extends AbstractHttpRequest
{
    private ByteArrayOutputStream mByteArray = new ByteArrayOutputStream();

    protected OutputStream getBodyOutputStream()
    {
        return mByteArray;
    }

    protected IHttpResponse executeRequest(HttpHeader header) throws IOException
    {
        byte[] data = mByteArray.toByteArray();
        return execute(header, data);
    }

    protected abstract IHttpResponse execute(HttpHeader header, byte[] data) throws IOException;
}
