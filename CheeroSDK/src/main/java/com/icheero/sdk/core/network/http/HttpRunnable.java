package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.api.CheeroRequest;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;

public class HttpRunnable implements Runnable
{
    private IHttpRequest mHttpRequest;
    private CheeroRequest mCheeroRequest;

    HttpRunnable(IHttpRequest httpRequest, CheeroRequest cheeroRequest)
    {
        this.mHttpRequest = httpRequest;
        this.mCheeroRequest = cheeroRequest;
    }

    @Override
    public void run()
    {
        try
        {
            OutputStream outputStream = mHttpRequest.getBody();
            if (outputStream != null)
                outputStream.write(mCheeroRequest.getData());
            IHttpResponse response = mHttpRequest.execute();
            mCheeroRequest.setContentType(response.getHeaders().getContentType());
            if (mCheeroRequest.getResponse() != null)
            {
                if (response.getStatus().isSuccess())
                    mCheeroRequest.getResponse().onSuccess(mCheeroRequest, new String(getData(response)));
                else
                    mCheeroRequest.getResponse().onFailure(response.getStatus().getStatusCode(), response.getStatus().getMessage());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            if (e instanceof SocketTimeoutException)
                mCheeroRequest.getResponse().onFailure(HttpStatus.REQUEST_TIMEOUT.getStatusCode(), HttpStatus.REQUEST_TIMEOUT.getMessage());
        }
        finally
        {
            HttpRequestEngine.getInstance().finish(mCheeroRequest);
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
