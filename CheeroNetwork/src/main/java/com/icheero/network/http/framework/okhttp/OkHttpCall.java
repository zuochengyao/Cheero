package com.icheero.network.http.framework.okhttp;

import android.support.annotation.NonNull;

import com.icheero.network.http.api.BaseApi;
import com.icheero.network.http.encapsulation.HttpStatus;
import com.icheero.network.http.encapsulation.IHttpCall;
import com.icheero.network.http.encapsulation.IHttpResponse;
import com.icheero.network.http.implement.HttpHeader;
import com.icheero.network.http.implement.entity.FormEntity;

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
        if (mHeader != null && mHeader.size() > 0)
        {
            for (Map.Entry<String, String> entry : mHeader.entrySet())
                mRequestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
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
                    {
                        mHeader.setContentType(response.header(HttpHeader.HEADER_CONTENT_TYPE));
                        mListener.onSuccess(mHeader.getContentType(), response.body().string());
                    }
                    else
                        mListener.onFailure(response.code(), response.body().string());
                }
            }
        });
    }

    @Override
    public void download() throws IOException
    {
        newCall().enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                mListener.onFailure(HttpStatus.NETWORK_ERROR.getStatusCode(), HttpStatus.NETWORK_ERROR.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                if (!response.isSuccessful())
                    mListener.onFailure(HttpStatus.NETWORK_ERROR.getStatusCode(), HttpStatus.NETWORK_ERROR.getMessage());
                else
                {
                    long contentLength = response.body() != null ? response.body().contentLength() : -1;
                    mListener.onSuccess(response.header(HttpHeader.HEADER_CONTENT_TYPE), contentLength + "");
                }
            }
        });
    }

    private Call newCall() throws IOException
    {
        switch (mMethod)
        {
            case POST:
            {
                if (mData != null)
                {
                    if (mData instanceof FormEntity)
                    {
                        FormBody.Builder builder = new FormBody.Builder();
                        for (Map.Entry<String, Object> entry : mData.entrySet())
                        {
                            builder.add(entry.getKey(), entry.getValue().toString());
                        }
                        mRequestBuilder.post(builder.build());
                    }
                    else if (mData instanceof MultipartEntity)
                    {
                        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                        for (Map.Entry<String, Object> entry : mData.entrySet())
                        {
                            Object value = entry.getValue();
                            if (value instanceof File)
                            {
                                File file = (File) value;
                                builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse(BaseApi.MEDIA_TYPE_MULTIPART), file));
                            }
                            else if (value instanceof byte[])
                            {
                                byte[] data = (byte[]) value;
                                builder.addFormDataPart(entry.getKey(), entry.getKey(), RequestBody.create(MediaType.parse(BaseApi.MEDIA_TYPE_MULTIPART), data));
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
                break;
            }
            case GET:
            {
                mRequestBuilder.get();
                break;
            }
            default:
                break;
        }
        return mClient.newCall(mRequestBuilder.build());
    }
}
