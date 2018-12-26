package com.icheero.sdk.core.network.http.encapsulation;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public interface IHttpCall extends IHttpHeader
{
    HttpMethod getMethod();

    URI getUri();

    OutputStream getBody();

    IHttpResponse execute() throws IOException;

    void enqueue() throws IOException;
}
