package com.icheero.sdk.core.network.http.framework.okhttp;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.http.implement.BufferHttpRequest;
import com.icheero.sdk.core.network.http.implement.HttpHeader;
import com.icheero.sdk.core.network.listener.IResponseListener;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequest extends BufferHttpRequest
{
    private OkHttpClient mClient;
    private HttpMethod mMethod;
    private String mUrl;
    private String mMimeType;

    public OkHttpRequest(OkHttpClient client, HttpMethod method, String url, String mimeType)
    {
        this.mClient = client;
        this.mMethod = method;
        this.mUrl = url;
        this.mMimeType = mimeType;
    }

    /**
     * generate a get request
     * @param fullUrl like: https://www.icheero.com?key1=val1&key2=val2&...
     * @return a get request
     */
    public static Request createGetRequest(@NonNull final String fullUrl)
    {
        return new Request.Builder().url(fullUrl).build();
    }

    /**
     * generate a get request
     * @param baseUrl like: https://www.icheero.com
     * @param paramMap like: key1, val1; key2, val2 ...
     * @return a get request
     */
    public static Request createGetRequest(@NonNull final String baseUrl, final Map<String, Object> paramMap)
    {
        StringBuilder sb = new StringBuilder(baseUrl);
        if (paramMap != null)
        {
            sb.append("?");
            for (Map.Entry<String, Object> entry : paramMap.entrySet())
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return createGetRequest(sb.toString().substring(0, sb.length() - 1));
    }

    /**
     * generate a get request
     * @param baseUrl like: https://www.icheero.com
     * @param paramStr like: key1=val1&key2=val2&...
     * @return a get request
     */
    public static Request createGetRequest(@NonNull String baseUrl, String paramStr)
    {
        if (!TextUtils.isEmpty(paramStr))
            baseUrl += ("?" + paramStr);
        return createGetRequest(baseUrl);
    }

    public static Request createPostRequest(@NonNull final String baseUrl, @NonNull Map<String, String> paramMap)
    {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : paramMap.entrySet())
            builder.add(entry.getKey(), entry.getValue());
        FormBody formBody = builder.build();
        return new Request.Builder().url(baseUrl).post(formBody).build();
    }

    public static Request createFilePostRequest(@NonNull final String baseUrl, @NonNull Map<String, Object> paramMap, String contentType)
    {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : paramMap.entrySet())
        {
            Object value = entry.getValue();
            if (value instanceof File)
            {
                File file = (File) value;
                builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse(contentType), file));
            }
            else if (value instanceof byte[])
            {
                byte[] data = (byte[]) value;
                builder.addFormDataPart(entry.getKey(), "faceid_image", RequestBody.create(MediaType.parse(contentType), data));
            }
            else
                builder.addFormDataPart(entry.getKey(), value.toString());
        }
        return new Request.Builder().url(baseUrl).post(builder.build()).build();
    }

    @Override
    protected IHttpResponse execute(HttpHeader header, byte[] data) throws IOException
    {
        boolean isBody = mMethod == HttpMethod.POST;
        RequestBody requestBody = null;
        if (isBody)
            requestBody = RequestBody.create(MediaType.parse(mMimeType), data);
        Request.Builder builder = new Request.Builder().url(mUrl).method(mMethod.name(), requestBody);
        for (Map.Entry<String, String> entry : header.entrySet())
            builder.addHeader(entry.getKey(), entry.getValue());
        Response response = mClient.newCall(builder.build()).execute();
        return new OkHttpResponse(response);
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
    public void enqueue(IResponseListener listener)
    {
        // TODO 异步处理
    }
}
