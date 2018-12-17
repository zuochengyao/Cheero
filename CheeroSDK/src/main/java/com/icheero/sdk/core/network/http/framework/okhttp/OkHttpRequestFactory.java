package com.icheero.sdk.core.network.http.framework.okhttp;

import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.IHttpRequestFactory;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author 左程耀
 *
 * OkHttpRequest 工厂类
 */
public class OkHttpRequestFactory implements IHttpRequestFactory
{
    private OkHttpClient mClient;

    public OkHttpRequestFactory()
    {
        this.mClient = new OkHttpClient();
    }

    public OkHttpRequestFactory(OkHttpClient client)
    {
        this.mClient = client;
    }

    public void setReadTimeout(int readTimeout)
    {
        this.mClient = mClient.newBuilder().readTimeout(readTimeout, TimeUnit.MILLISECONDS).build();
    }

    public void setWriteTimeout(int writeTimeout)
    {
        this.mClient = mClient.newBuilder().writeTimeout(writeTimeout, TimeUnit.MILLISECONDS).build();
    }

    public void setConnectionTimeout(int connectionTimeout)
    {
        this.mClient = mClient.newBuilder().connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS).build();
    }

    @Override
    public IHttpRequest createHttpRequest(URI uri, HttpMethod method, String mimeType)
    {
        return new OkHttpRequest(mClient, method, uri.toString(), mimeType);
    }
}
