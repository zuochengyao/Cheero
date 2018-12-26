package com.icheero.sdk.core.network.http.framework.okhttp;

import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author 左程耀
 *
 * OkHttpCall 工厂类
 */
public class OkHttpRequestFactory implements IOkHttpRequestFactory
{
    private OkHttpClient mClient;

    public OkHttpRequestFactory()
    {
        this.mClient = OkHttpManager.getInstance().getOkHttpClient();
    }

    @Override
    public void setReadTimeout(int readTimeout)
    {
        this.mClient = mClient.newBuilder().readTimeout(readTimeout, TimeUnit.SECONDS).build();
    }

    public void setWriteTimeout(int writeTimeout)
    {
        this.mClient = mClient.newBuilder().writeTimeout(writeTimeout, TimeUnit.SECONDS).build();
    }

    @Override
    public void setConnectionTimeout(int connectionTimeout)
    {
        this.mClient = mClient.newBuilder().connectTimeout(connectionTimeout, TimeUnit.SECONDS).build();
    }

    @Override
    public void setRetryOnConnectionFailure(boolean retry)
    {
        this.mClient = mClient.newBuilder().retryOnConnectionFailure(retry).build();
    }

    @Override
    public IHttpCall getHttpCall(HttpRequest request)
    {
        return new OkHttpCall(mClient, request);
    }
}
