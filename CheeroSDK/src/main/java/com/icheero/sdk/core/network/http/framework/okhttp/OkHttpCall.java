package com.icheero.sdk.core.network.http.framework.okhttp;

import android.support.annotation.NonNull;

import com.icheero.sdk.base.BaseApi;
import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.encapsulation.IHttpCall;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.http.implement.HttpHeader;
import com.icheero.sdk.core.network.http.implement.entity.FormEntity;
import com.icheero.sdk.core.network.http.implement.entity.MultipartEntity;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpCall implements IHttpCall
{
    private OkHttpClient mClient;
    private String mUrl;
    private HttpMethod mMethod;
    private String mMediaType;
    private HttpHeader mHeader;
    private AbstractHttpEntity mData;
    private HttpResponse mListener;

    private Request.Builder mRequestBuilder;

    OkHttpCall(String url, HttpMethod method, String mediaType, HttpHeader header, AbstractHttpEntity data, HttpResponse listener)
    {
        this.mClient = OkHttpManager.getInstance().getOkHttpClient();
        this.mUrl = url;
        this.mMethod = method;
        this.mMediaType = mediaType;
        this.mHeader = header;
        this.mData = data;
        this.mListener = listener;
        init();
    }

    private void init()
    {
        this.mRequestBuilder = new Request.Builder().url(mUrl);
        for (Map.Entry<String, String> entry : mHeader.entrySet())
            mRequestBuilder.addHeader(entry.getKey(), entry.getValue());
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
        Response response = newCall().execute();
        mHeader.setContentType(response.header(HttpHeader.HEADER_CONTENT_TYPE));
        return new OkHttpResponse(response);
    }

    @Override
    public void enqueue() throws IOException
    {
        newCall().enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {
                mListener.onFailure(HttpStatus.REQUEST_TIMEOUT.getStatusCode(), HttpStatus.REQUEST_TIMEOUT.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                if (response.body() != null)
                {
                    if (response.isSuccessful())
                        mListener.onSuccess(response.header(HttpHeader.HEADER_CONTENT_TYPE), response.body().string());
                    else
                        mListener.onFailure(response.code(), response.body().string());
                }
            }
        });
    }

    private Call newCall() throws IOException
    {
        if (mMethod == HttpMethod.POST)
        {
            if (mData != null)
            {
                if (mData instanceof FormEntity)
                {
                    FormBody.Builder builder = new FormBody.Builder();
                    for (Object obj : mData.entrySet())
                    {
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) obj;
                        builder.add(entry.getKey(), entry.getValue());
                    }
                    mRequestBuilder.post(builder.build());
                }
                else if (mData instanceof MultipartEntity)
                {
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    for (Object obj : mData.entrySet())
                    {
                        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) obj;
                        Object value = entry.getValue();
                        if (value instanceof File)
                        {
                            File file = (File) value;
                            builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse(BaseApi.MEDIA_TYPE_MULTIPART), file));
                        }
                        else if (value instanceof byte[])
                        {
                            byte[] data = (byte[]) value;
                            builder.addFormDataPart(entry.getKey(), "data", RequestBody.create(MediaType.parse(BaseApi.MEDIA_TYPE_MULTIPART), data));
                        }
                        else
                            builder.addFormDataPart(entry.getKey(), value.toString());
                    }
                    mRequestBuilder.post(builder.build());
                }
                else
                {
                    RequestBody requestBody = RequestBody.create(MediaType.parse(mMediaType), mData.getBytes());
                    mRequestBuilder.method(mMethod.name(), requestBody);
                }
            }
            else
                mRequestBuilder.post(RequestBody.create(MediaType.parse(mMediaType), ""));
        }
        return mClient.newCall(mRequestBuilder.build());
    }
}
