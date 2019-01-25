package com.icheero.network.http.encapsulation;

import com.icheero.network.http.HttpRequest;

public interface IHttpRequestFactory
{
    void setReadTimeout(int readTimeout);

    void setConnectTimeout(int connectTimeout);

    IHttpCall getHttpCall(HttpRequest request);
}
