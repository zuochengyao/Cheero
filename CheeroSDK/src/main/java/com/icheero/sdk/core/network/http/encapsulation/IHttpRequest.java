package com.icheero.sdk.core.network.http.encapsulation;

import com.icheero.sdk.core.network.listener.IResponseListener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public interface IHttpRequest extends IHttpHeader
{
    HttpMethod getMethod();

    URI getUri();

    OutputStream getBody();

    IHttpResponse execute() throws IOException;

    void enqueue(IResponseListener listener);
}
