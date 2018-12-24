package com.icheero.sdk.core.network.http.framework.okhttp;

import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author 左程耀
 *
 * OkHttpRequest 工厂类
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
        this.mClient = mClient.newBuilder().readTimeout(readTimeout, TimeUnit.MILLISECONDS).build();
    }

    public void setWriteTimeout(int writeTimeout)
    {
        this.mClient = mClient.newBuilder().writeTimeout(writeTimeout, TimeUnit.MILLISECONDS).build();
    }

    @Override
    public void setConnectionTimeout(int connectionTimeout)
    {
        this.mClient = mClient.newBuilder().connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS).build();
    }

    @Override
    public void setRetryOnConnectionFailure(boolean retry)
    {
        this.mClient = mClient.newBuilder().retryOnConnectionFailure(retry).build();
    }

    @Override
    public IHttpRequest createHttpRequest(URI uri, HttpMethod method, String mediaType)
    {
        return new OkHttpRequest(mClient, method, uri.toString(), mediaType);
    }
}
