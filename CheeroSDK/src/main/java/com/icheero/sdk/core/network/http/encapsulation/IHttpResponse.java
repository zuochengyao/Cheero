package com.icheero.sdk.core.network.http.encapsulation;


import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface IHttpResponse extends IHttpHeader, Closeable
{
    HttpStatus getStatus();

    String getStatusMessage();

    InputStream getBody() throws IOException;

    void close() throws IOException;
}
