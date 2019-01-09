package com.icheero.sdk.core.network.http.framework.okhttp;

import android.support.annotation.NonNull;

import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.http.HttpSecure;
import com.icheero.sdk.core.network.listener.IDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp 工具类
 * Created by zuochengyao on 2018/3/1.
 */
public class OkHttpManager
{
    private static final Class<OkHttpManager> TAG = OkHttpManager.class;

    public static final int NETWORK_STATUS_CODE_SUCCESS = 200;
    public static final int NETWORK_STATUS_CODE_ERROR = 400;
    public static final int NETWORK_STATUS_CODE_TASK_RUNNING = 400;
    public static final int NETWORK_STATUS_CODE_NOT_FOUND = 404;
    public static final int NETWORK_STATUS_CODE_TIMEOUT = 408;

    public static final String NETWORK_ERROR = "Network Error";
    public static final String NETWORK_ERROR_MSG_NOT_FOUND = "API NOT FOUND";
    public static final String NETWORK_ERROR_MSG_TIMEOUT = "Request TIMEOUT";
    public static final String NETWORK_ERROR_MSG_CONTENT_LENGTH = "Content length -1";
    public static final String NETWORK_ERROR_MSG_TASK_RUNNING = "Task Running";

    private static volatile OkHttpManager mInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpManager()
    {
        this.mOkHttpClient = new OkHttpClient.Builder()
                .hostnameVerifier(HttpSecure.hv)
                .sslSocketFactory(HttpSecure.getSocketFactory(), HttpSecure.initX509TrustManager()) // 支持https
                .build();
    }

    public static OkHttpManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (TAG)
            {
                if (mInstance == null)
                    mInstance = new OkHttpManager();
            }
        }
        return mInstance;
    }

    OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }

    public void setReadTimeout(int readTimeout)
    {
        this.mOkHttpClient = mOkHttpClient.newBuilder().readTimeout(readTimeout, TimeUnit.SECONDS).build();
    }

    void setWriteTimeout(int writeTimeout)
    {
        this.mOkHttpClient = mOkHttpClient.newBuilder().writeTimeout(writeTimeout, TimeUnit.SECONDS).build();
    }

    void setConnectionTimeout(int connectionTimeout)
    {
        this.mOkHttpClient = mOkHttpClient.newBuilder().connectTimeout(connectionTimeout, TimeUnit.SECONDS).build();
    }

    void setRetryOnConnectionFailure(boolean retry)
    {
        this.mOkHttpClient = mOkHttpClient.newBuilder().retryOnConnectionFailure(retry).build();
    }

    public Response syncRequest(@NonNull Request request)
    {
        try
        {
            return mOkHttpClient.newCall(request).execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Response syncDownload(String url)
    {
        try
        {
            Request request = new Request.Builder().url(url).build();
            return mOkHttpClient.newCall(request).execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void asyncDownload(@NonNull Request request, @NonNull Callback callback)
    {
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    public void asyncDownload(@NonNull Request request, @NonNull IDownloadListener listener)
    {
        asyncDownload(request, new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                listener.onFailure(408, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (!response.isSuccessful())
                    listener.onFailure(response.code(), response.message());
                else
                {
                    File file = IOManager.getInstance().getCacheFileByName(request.url().toString());
                    byte[] buffer = new byte[500 * 1024];
                    int len;
                    int progress = 0;
                    long contentLength = response.body().contentLength();
                    FileOutputStream out = new FileOutputStream(file);
                    InputStream in = response.body().byteStream();

                    while ((len = in.read(buffer, 0, buffer.length)) != -1)
                    {
                        out.write(buffer, 0, len);
                        out.flush();
                        if (contentLength > 0)
                            listener.onProgress((int) ((long) (progress += len) / contentLength));
                    }
                    listener.onSuccess(file);
                }
            }
        });
    }

    public Response syncDownloadByRange(String url, long start, long end)
    {
        Request request = new Request.Builder().url(url).addHeader("Range", "bytes=" + start + "-" + end).build();
        try
        {
            return mOkHttpClient.newCall(request).execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Request createGetRequest(String url)
    {
        return new Request.Builder().url(url).build();
    }
}
