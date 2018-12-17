package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.IHttpRequest;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;

import java.net.URI;

public interface IHttpRequestFactory
{
    IHttpRequest createHttpRequest(URI uri, HttpMethod method, String mimeType);
}
