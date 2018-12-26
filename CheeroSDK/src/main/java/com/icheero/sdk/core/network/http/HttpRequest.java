package com.icheero.sdk.core.network.http;

import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;

/**
 * @author 左程耀
 * 2018年12月18日17:19:50
 */
public class HttpRequest
{
    private String mUrl;
    private HttpMethod mMethod;
    private byte[] mData;
    private String mMediaType;
    private String mContentType;
    private HttpResponse mResponse;

    public String getUrl()
    {
        return mUrl;
    }

    public void setUrl(String url)
    {
        this.mUrl = url;
    }

    public HttpMethod getMethod()
    {
        return mMethod;
    }

    public void setMethod(HttpMethod method)
    {
        this.mMethod = method;
    }

    public byte[] getData()
    {
        return mData;
    }

    public void setData(byte[] data)
    {
        this.mData = data;
    }

    public String getMediaType()
    {
        return mMediaType;
    }

    public void setMediaType(String mediaType)
    {
        this.mMediaType = mediaType;
    }

    public String getContentType()
    {
        return mContentType;
    }

    public void setContentType(String contentType)
    {
        this.mContentType = contentType;
    }

    public HttpResponse getResponse()
    {
        return mResponse;
    }

    public void setResponse(HttpResponse response)
    {
        this.mResponse = response;
    }
}
