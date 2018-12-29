package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.IOException;

public abstract class AbstractHttpCall implements IHttpCall
{
    protected static final String GZIP = "gzip";

    private boolean isExecuted = false;

    @Override
    public IHttpResponse execute() throws IOException
    {
        checkExecuted();
        return execute(getHeaders());
    }

    @Override
    public void enqueue() throws IOException
    {
        checkExecuted();
        enqueue(getHeaders());
    }

    protected void checkExecuted() throws IOException
    {
        synchronized (this)
        {
            if (isExecuted)
                throw new IllegalStateException("The Request Already Executed");
            isExecuted = true;
        }
    }

    protected abstract HttpHeader getHeaders();

    protected abstract IHttpResponse execute(HttpHeader header) throws IOException;

    protected abstract void enqueue(HttpHeader header);
}
