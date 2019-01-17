package com.icheero.sdk.core.network.http.framework.apache;

import com.icheero.sdk.base.BaseApi;
import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.http.implement.AbstractAsyncHttpCall;
import com.icheero.sdk.core.network.http.implement.HttpHeader;
import com.icheero.sdk.core.network.http.implement.entity.FormEntity;
import com.icheero.sdk.core.network.http.implement.entity.MultipartEntity;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientCall extends AbstractAsyncHttpCall
{
    private HttpClient mHttpClient;
    private String mUrl;
    private HttpMethod mMethod;
    private HttpHeader mHeader;
    private AbstractHttpEntity mData;
    private HttpResponse mListener;

    HttpClientCall(HttpClient httpClient, String url, HttpMethod method, HttpHeader header, AbstractHttpEntity data, HttpResponse listener)
    {
        this.mHttpClient = httpClient;
        this.mUrl = url;
        this.mMethod = method;
        this.mHeader = header;
        this.mData = data;
        this.mListener = listener;
    }

    @Override
    protected HttpHeader getHeaders()
    {
        return mHeader;
    }

    @Override
    public HttpMethod getMethod()
    {
        return mMethod;
    }

    @Override
    public URI getUri()
    {
        return URI.create(mUrl);
    }

    @Override
    public IHttpResponse execute() throws IOException
    {
        org.apache.http.HttpResponse response = mHttpClient.execute(getHttpMethod());
        mHeader.setContentType(response.getFirstHeader(HttpHeader.HEADER_CONTENT_TYPE).getValue());
        return new HttpClientResponse(response);
    }

    @Override
    public HttpResponse getListener()
    {
        return mListener;
    }

    private HttpUriRequest getHttpMethod() throws IOException
    {
        HttpUriRequest request;
        switch (mMethod)
        {
            default:
            case GET:
            {
                request = new HttpGet(mUrl);
                break;
            }
            case POST:
            {
                request = new HttpPost(mUrl);
                if (mData != null && mData.size() > 0)
                {
                    if (mData instanceof FormEntity)
                    {
                        List<NameValuePair> postParams = new ArrayList<>();
                        for (Map.Entry<String, Object> entry : mData.entrySet())
                            postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                        ((HttpPost) request).setEntity(new UrlEncodedFormEntity(postParams, BaseApi.ENCODING_UTF8));
                    }
                    else if (mData instanceof MultipartEntity)
                    {
                        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                        for (Map.Entry<String, Object> entry : mData.entrySet())
                        {
                            Object value = entry.getValue();
                            if (value instanceof File)
                            {
                                File file = (File) value;
                                builder.addBinaryBody(entry.getKey(), file, ContentType.MULTIPART_FORM_DATA, file.getName());
                            }
                            else if (value instanceof byte[])
                            {
                                byte[] data = (byte[]) value;
                                builder.addBinaryBody(entry.getKey(), data, ContentType.MULTIPART_FORM_DATA, entry.getKey());
                            }
                            else
                                builder.addTextBody(entry.getKey(), value.toString(), ContentType.MULTIPART_FORM_DATA);
                        }
                        ((HttpPost) request).setEntity(builder.build());
                    }
                }
                break;
            }
            case PUT:
                request = new HttpPut(mUrl);
                break;
            case TRACE:
                request = new HttpTrace(mUrl);
                break;
            case DELETE:
                request = new HttpDelete(mUrl);
                break;
            case OPTIONS:
                request = new HttpOptions(mUrl);
                break;
        }
        if (mHeader != null && mHeader.size() > 0)
        {
            for (Map.Entry<String, String> entry : mHeader.entrySet())
                request.addHeader(entry.getKey(), entry.getValue());
        }
        return request;
    }
}
