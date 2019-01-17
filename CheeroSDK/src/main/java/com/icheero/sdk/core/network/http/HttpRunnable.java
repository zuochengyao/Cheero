package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.http.implement.AbstractAsyncHttpCall;

import java.io.IOException;

public class HttpRunnable implements Runnable
{
    private AbstractAsyncHttpCall mHttpCall;
    private AbstractAsyncHttpCall.AsyncCallback mCallback;

    HttpRunnable(AbstractAsyncHttpCall httpCall, AbstractAsyncHttpCall.AsyncCallback response)
    {
        this.mHttpCall = httpCall;
        this.mCallback = response;
    }

    @Override
    public void run()
    {
        try
        {
            IHttpResponse response = mHttpCall.execute();
            if (mCallback != null)
                mCallback.onCallback(response);
        }
        catch (IOException e)
        {
            if (mCallback != null)
                mCallback.onException(e);
        }
        finally
        {
            HttpThreadPool.getInstance().finish(mHttpCall);
        }
    }
}
