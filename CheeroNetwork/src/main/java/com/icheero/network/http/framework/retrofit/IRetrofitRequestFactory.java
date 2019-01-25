package com.icheero.network.http.framework.retrofit;

import com.icheero.network.http.encapsulation.IHttpRequestFactory;

public interface IRetrofitRequestFactory extends IHttpRequestFactory
{
    void setWriteTimeout(int readTimeout);

    void setRetryOnConnectionFailure(boolean retry);
}
