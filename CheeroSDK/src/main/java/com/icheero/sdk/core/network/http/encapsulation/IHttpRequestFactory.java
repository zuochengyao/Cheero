package com.icheero.sdk.core.network.http.encapsulation;

import com.icheero.sdk.core.network.http.HttpRequest;

public interface IHttpRequestFactory
{
    void setReadTimeout(int readTimeout);

    void setConnectTimeout(int connectTimeout);

    IHttpCall getHttpCall(HttpRequest request);
}
