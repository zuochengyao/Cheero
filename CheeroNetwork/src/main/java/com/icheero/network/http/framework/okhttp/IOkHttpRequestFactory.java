package com.icheero.network.http.framework.okhttp;

import com.icheero.network.http.encapsulation.IHttpRequestFactory;

public interface IOkHttpRequestFactory extends IHttpRequestFactory
{
    void setWriteTimeout(int readTimeout);

    void setRetryOnConnectionFailure(boolean retry);
}
