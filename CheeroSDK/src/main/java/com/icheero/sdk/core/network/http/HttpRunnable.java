package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class HttpRunnable implements Runnable
{
    private IHttpCall mHttpCall;
    private HttpRequest mRequest;

    HttpRunnable(IHttpCall httpCall, HttpRequest request)
    {
        this.mHttpCall = httpCall;
        this.mRequest = request;
    }

    @Override
    public void run()
    {
        try
        {
            IHttpResponse response = mHttpCall.execute(mRequest);
            mRequest.setContentType(response.getHeaders().getContentType());
            if (mRequest.getResponse() != null)
            {
                if (response.getStatus().isSuccess())
                    mRequest.getResponse().onSuccess(mRequest, new String(IOManager.getInstance().getResponseData(response)));
                else
                    mRequest.getResponse().onFailure(response.getStatus().getStatusCode(), response.getStatus().getMessage());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException)
                mRequest.getResponse().onFailure(HttpStatus.REQUEST_TIMEOUT.getStatusCode(), HttpStatus.REQUEST_TIMEOUT.getMessage());
        }
        finally
        {
            HttpRequestEngine.getInstance().finish(mRequest);
        }
    }
}
