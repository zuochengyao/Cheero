package com.icheero.network.http;

import com.icheero.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.network.http.encapsulation.HttpMethod;
import com.icheero.network.http.implement.HttpHeader;

/**
 * @author 左程耀
 * 2018年12月18日17:19:50
 */
public class HttpRequest
{
    private String mUrl;
    private HttpMethod mMethod;
    private AbstractHttpEntity mData;
    private String mMediaType;
    private HttpHeader mHeader = new HttpHeader();
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

    public AbstractHttpEntity getData()
    {
        return mData;
    }

    public void setData(AbstractHttpEntity data)
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

    public HttpHeader getHeader()
    {
        return mHeader;
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
