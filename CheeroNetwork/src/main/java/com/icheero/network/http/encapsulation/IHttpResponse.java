package com.icheero.network.http.encapsulation;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface IHttpResponse extends IHttpHeader, Closeable
{
    long getContentLength();

    HttpStatus getStatus();

    String getStatusMessage();

    InputStream getBody() throws IOException;

    void close() throws IOException;
}
