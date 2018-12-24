package com.icheero.sdk.core.network.http.framework.retrofit;

import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;

import java.io.IOException;
import java.net.URI;

import retrofit2.Retrofit;

public class RetrofitHttpRequestFactory implements IRetrofitRequestFactory
{
    private Retrofit mRetrofit;

    public RetrofitHttpRequestFactory()
    {
        mRetrofit = RetrofitManager.getInstance().getRetrofit();
    }

    @Override
    public void setReadTimeout(int readTimeout)
    {
    }

    @Override
    public void setWriteTimeout(int readTimeout)
    {

    }

    @Override
    public void setConnectionTimeout(int connectionTimeout)
    {

    }

    @Override
    public void setRetryOnConnectionFailure(boolean retry)
    {

    }

    @Override
    public IHttpRequest createHttpRequest(URI uri, HttpMethod method, String mediaType) throws IOException
    {
        return null;
    }
}
