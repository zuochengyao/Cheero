package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.http.implement.AbstractAsyncHttpCall;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class HttpRunnable implements Runnable
{
    private AbstractAsyncHttpCall mHttpCall;
    private HttpResponse mResponse;

    HttpRunnable(AbstractAsyncHttpCall httpCall, HttpResponse response)
    {
        this.mHttpCall = httpCall;
        this.mResponse = response;
    }

    @Override
    public void run()
    {
        try
        {
            IHttpResponse response = mHttpCall.execute();
            if (mResponse != null)
            {
                if (response.getStatus().isSuccess())
                    mResponse.onSuccess(response.getHeaders().getContentType(), new String(IOManager.getInstance().getResponseData(response)));
                else
                    mResponse.onFailure(response.getStatus().getStatusCode(), response.getStatusMessage());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException)
                mResponse.onFailure(HttpStatus.REQUEST_TIMEOUT.getStatusCode(), HttpStatus.REQUEST_TIMEOUT.getMessage());
            else
                mResponse.onFailure(HttpStatus.UNKNOWN.getStatusCode(), HttpStatus.UNKNOWN.getMessage());
        }
        finally
        {
            HttpThreadPool.getInstance().finish(mHttpCall);
        }
    }
}
