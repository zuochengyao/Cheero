package com.icheero.sdk.core.network.http.framework.okhttp;

import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;

/**
 * @author 左程耀
 *
 * OkHttpCall 工厂类
 */
public class OkHttpRequestFactory implements IOkHttpRequestFactory
{
    @Override
    public void setReadTimeout(int readTimeout)
    {
        OkHttpManager.getInstance().setReadTimeout(readTimeout);
    }

    public void setWriteTimeout(int writeTimeout)
    {
        OkHttpManager.getInstance().setWriteTimeout(writeTimeout);
    }

    @Override
    public void setConnectionTimeout(int connectionTimeout)
    {
        OkHttpManager.getInstance().setConnectionTimeout(connectionTimeout);
    }

    @Override
    public void setRetryOnConnectionFailure(boolean retry)
    {
        OkHttpManager.getInstance().setRetryOnConnectionFailure(retry);
    }

    @Override
    public IHttpCall getHttpCall(HttpRequest request)
    {
        return new OkHttpCall(request.getUrl(), request.getMethod(), request.getMediaType(), request.getHeader(), request.getData(), request.getResponse());
    }
}
