package com.icheero.sdk.core.network.http.framework.okhttp;

import com.icheero.sdk.core.network.http.implement.AbstractHttpResponse;
import com.icheero.sdk.core.network.http.implement.HttpHeader;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;

import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpResponse extends AbstractHttpResponse
{
    private Response mResponse;
    private HttpHeader mHttpHeader;

    OkHttpResponse(Response response)
    {
        this.mResponse = response;
    }

    @Override
    protected InputStream getBodyStream()
    {
        return mResponse.body() != null ? mResponse.body().byteStream() : null;
    }

    @Override
    protected void closeBodyStream()
    {
        ResponseBody body = mResponse.body();
        if (body != null)
            body.close();
    }

    @Override
    public long getContentLength()
    {
        return mResponse.body() != null ? mResponse.body().contentLength() : 0;
    }

    @Override
    public HttpStatus getStatus()
    {
        return HttpStatus.getValue(mResponse.code());
    }

    @Override
    public String getStatusMessage()
    {
        return mResponse.message();
    }

    @Override
    public HttpHeader getHeaders()
    {
        if (mHttpHeader == null)
            mHttpHeader = new HttpHeader();
        for (String name : mResponse.headers().names())
            mHttpHeader.set(name, mResponse.headers().get(name));
        return mHttpHeader;
    }
}
