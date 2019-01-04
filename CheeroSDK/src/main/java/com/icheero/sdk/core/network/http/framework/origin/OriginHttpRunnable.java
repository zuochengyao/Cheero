package com.icheero.sdk.core.network.http.framework.origin;

import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class OriginHttpRunnable implements Runnable
{
    private OriginHttpCall mOriginHttpCall;
    private HttpResponse mResponse;

    OriginHttpRunnable(OriginHttpCall originHttpCall, HttpResponse response)
    {
        this.mOriginHttpCall = originHttpCall;
        this.mResponse = response;
    }

    @Override
    public void run()
    {
        try
        {
            IHttpResponse response = mOriginHttpCall.execute();
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
            OriginHttpManager.getInstance().finish(mOriginHttpCall);
        }
    }
}
