package com.icheero.sdk.core.network.http.implement;

import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.HttpThreadPool;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.util.FileUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

public abstract class AbstractAsyncHttpCall extends AbstractHttpCall
{
    private AsyncCallback mCallback;

    @Override
    public void enqueue()
    {
        this.mCallback = new AsyncCallback()
        {
            @Override
            public void onCallback(IHttpResponse response)
            {
                if (getListener() != null)
                {
                    if (response.getStatus().isSuccess())
                    {
                        try
                        {
                            getListener().onSuccess(response.getHeaders().getContentType(), new String(FileUtils.getInputStreamData(response.getContentLength(), response.getBody())));
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                        getListener().onFailure(response.getStatus().getStatusCode(), response.getStatusMessage());
                }
            }

            @Override
            public void onException(IOException e)
            {
                e.printStackTrace();
                if (e instanceof SocketTimeoutException)
                    getListener().onFailure(HttpStatus.REQUEST_TIMEOUT.getStatusCode(), HttpStatus.REQUEST_TIMEOUT.getMessage());
                else
                    getListener().onFailure(HttpStatus.UNKNOWN.getStatusCode(), HttpStatus.UNKNOWN.getMessage());
            }
        };
        HttpThreadPool.getInstance().enqueue(this);
    }

    @Override
    public void download()
    {
        this.mCallback = new AsyncCallback()
        {
            @Override
            public void onCallback(IHttpResponse response)
            {
                if (getListener() != null)
                {
                    if (response.getStatus().isSuccess())
                        getListener().onSuccess(response.getHeaders().getContentType(), response.getContentLength() + "");
                    else
                        getListener().onFailure(response.getStatus().getStatusCode(), response.getStatusMessage());
                }
            }

            @Override
            public void onException(IOException e)
            {
                e.printStackTrace();
                if (e instanceof SocketTimeoutException)
                    getListener().onFailure(HttpStatus.REQUEST_TIMEOUT.getStatusCode(), HttpStatus.REQUEST_TIMEOUT.getMessage());
                else
                    getListener().onFailure(HttpStatus.UNKNOWN.getStatusCode(), HttpStatus.UNKNOWN.getMessage());
            }
        };
        HttpThreadPool.getInstance().enqueue(this);
    }

    public abstract HttpResponse getListener();

    public AsyncCallback getCallback()
    {
        return mCallback;
    }

    public interface AsyncCallback
    {
        void onCallback(IHttpResponse response);

        void onException(IOException e);
    }
}
