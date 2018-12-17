package com.icheero.sdk.core.network.http;

import java.net.URI;

public interface IHttpRequestFactory
{
    IHttpRequest createHttpRequest(URI uri, IHttpRequest.HttpMethod method);
}
