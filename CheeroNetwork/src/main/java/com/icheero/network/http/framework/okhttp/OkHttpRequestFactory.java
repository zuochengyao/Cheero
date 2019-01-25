package com.icheero.network.http.framework.okhttp;

import com.icheero.network.http.encapsulation.IHttpCall;

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
    public void setConnectTimeout(int connectTimeout)
    {
        OkHttpManager.getInstance().setConnectionTimeout(connectTimeout);
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
