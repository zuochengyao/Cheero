package com.icheero.sdk.core.network.http.encapsulation;

import java.io.IOException;
import java.net.URI;

public interface IHttpRequestFactory
{
    void setReadTimeout(int readTimeout);

    void setConnectionTimeout(int connectionTimeout);

    IHttpRequest createHttpRequest(URI uri, HttpMethod method, String mimeType) throws IOException;
}
