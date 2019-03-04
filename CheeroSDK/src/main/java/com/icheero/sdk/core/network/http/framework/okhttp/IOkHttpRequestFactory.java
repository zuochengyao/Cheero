package com.icheero.sdk.core.network.http.framework.okhttp;

import com.icheero.sdk.core.network.http.encapsulation.IHttpRequestFactory;

public interface IOkHttpRequestFactory extends IHttpRequestFactory
{
    void setWriteTimeout(int readTimeout);

    void setRetryOnConnectionFailure(boolean retry);
}
