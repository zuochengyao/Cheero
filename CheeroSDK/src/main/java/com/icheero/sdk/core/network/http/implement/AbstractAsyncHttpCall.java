package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.HttpThreadPool;

public abstract class AbstractAsyncHttpCall extends AbstractHttpCall
{
    @Override
    public void enqueue()
    {
        HttpThreadPool.getInstance().enqueue(this);
    }

    public abstract HttpResponse getListener();
}
