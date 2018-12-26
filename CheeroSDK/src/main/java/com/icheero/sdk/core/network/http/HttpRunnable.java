package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;

public class HttpRunnable implements Runnable
{
    private IHttpCall mHttpRequest;
    private HttpRequest mBaseRequest;

    HttpRunnable(IHttpCall httpRequest, HttpRequest baseRequest)
    {
        this.mHttpRequest = httpRequest;
        this.mBaseRequest = baseRequest;
    }

    @Override
    public void run()
    {
        try
        {
            OutputStream outputStream = mHttpRequest.getBody();
            if (outputStream != null)
                outputStream.write(mBaseRequest.getData());
            IHttpResponse response = mHttpRequest.execute();
            mBaseRequest.setContentType(response.getHeaders().getContentType());
            if (mBaseRequest.getResponse() != null)
            {
                if (response.getStatus().isSuccess())
                    mBaseRequest.getResponse().onSuccess(mBaseRequest, new String(getData(response)));
                else
                    mBaseRequest.getResponse().onFailure(response.getStatus().getStatusCode(), response.getStatus().getMessage());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException)
                mBaseRequest.getResponse().onFailure(HttpStatus.REQUEST_TIMEOUT.getStatusCode(), HttpStatus.REQUEST_TIMEOUT.getMessage());
        }
        finally
        {
            HttpRequestEngine.getInstance().finish(mBaseRequest);
        }
    }

    /**
     * 获取响应body数据
     */
    private byte[] getData(IHttpResponse response)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) response.getContentLength());
        int length;
        byte[] data = new byte[1024];
        try
        {
            while ((length = response.getBody().read(data)) != -1)
                outputStream.write(data, 0, length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
