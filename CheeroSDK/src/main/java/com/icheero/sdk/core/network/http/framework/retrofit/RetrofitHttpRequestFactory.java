package com.icheero.sdk.core.network.http.framework.retrofit;

import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;

import retrofit2.Retrofit;

public class RetrofitHttpRequestFactory implements IRetrofitRequestFactory
{
    private Retrofit mRetrofit;

    public RetrofitHttpRequestFactory()
    {
        mRetrofit = RetrofitManager.getInstance().getRetrofit();
    }

    @Override
    public void setWriteTimeout(int readTimeout)
    {
        
    }

    @Override
    public void setRetryOnConnectionFailure(boolean retry)
    {

    }

    @Override
    public void setReadTimeout(int readTimeout)
    {

    }

    @Override
    public void setConnectTimeout(int connectTimeout)
    {

    }

    @Override
    public IHttpCall getHttpCall(HttpRequest request)
    {
        return null;
    }
}
