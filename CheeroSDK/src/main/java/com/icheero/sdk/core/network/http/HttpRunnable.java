package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.api.CheeroRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpRunnable implements Runnable
{
    private IHttpRequest mHttpRequest;
    private CheeroRequest mCheeroRequest;

    public HttpRunnable(IHttpRequest httpRequest, CheeroRequest cheeroRequest)
    {
        this.mHttpRequest = httpRequest;
        this.mCheeroRequest = cheeroRequest;
    }

    @Override
    public void run()
    {
        try
        {
            mHttpRequest.getBody().write(mCheeroRequest.getData());
            IHttpResponse response = mHttpRequest.execute();
            if (response.getStatus().isSuccess())
            {
                if (mCheeroRequest.getResponse() != null)
                    mCheeroRequest.getResponse().success(mCheeroRequest, new String(getData(response)));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取响应body数据
     */
    public byte[] getData(IHttpResponse response)
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
