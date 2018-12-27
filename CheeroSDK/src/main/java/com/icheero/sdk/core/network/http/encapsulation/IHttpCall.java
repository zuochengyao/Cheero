package com.icheero.sdk.core.network.http.encapsulation;

import com.icheero.sdk.core.network.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public interface IHttpCall extends IHttpHeader
{
    HttpMethod getMethod();

    URI getUri();

    OutputStream getBody();

    IHttpResponse execute(HttpRequest request) throws IOException;

    void enqueue(HttpRequest request) throws IOException;
}
