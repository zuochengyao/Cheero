package com.icheero.sdk.core.network.http.framework.retrofit;

import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;

public interface IRetrofitRequestFactory extends IHttpRequestFactory
{
    void setWriteTimeout(int readTimeout);

    void setRetryOnConnectionFailure(boolean retry);
}
