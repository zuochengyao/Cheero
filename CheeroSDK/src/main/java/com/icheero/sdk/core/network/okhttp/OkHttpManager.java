package com.icheero.sdk.core.network.okhttp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.IHttpManager;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.core.network.listener.IResponseListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp 工具类
 * Created by zuochengyao on 2018/3/1.
 */
public class OkHttpManager implements IHttpManager
{
    private static final Class<OkHttpManager> TAG = OkHttpManager.class;

    private static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static final int NETWORK_STATUS_CODE_SUCCESS = 200;
    public static final int NETWORK_STATUS_CODE_ERROR = 400;
    public static final int NETWORK_STATUS_CODE_NOT_FOUND = 404;
    public static final int NETWORK_STATUS_CODE_TIMEOUT = 408;

    public static final String NETWORK_ERROR = "Network Error";
    public static final String NETWORK_ERROR_MSG_NOT_FOUND = "API NOT FOUND";
    public static final String NETWORK_ERROR_MSG_TIMEOUT = "Request TIMEOUT";
    public static final String NETWORK_ERROR_MSG_CONTENT_LENGTH = "Content length -1";

    private static volatile OkHttpManager mInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpManager()
    {
        this.mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .sslSocketFactory(initSSLSocketFactory(), initX509TrustManager()) // 支持https
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

    @Override
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

    @Override
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

    @Override
    public void asyncRequest(@NonNull Request request, @NonNull IResponseListener listener)
    {
        mOkHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                listener.onFailure(408, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                if (response.isSuccessful())
                    listener.onSuccess(response.message());
                else
                    listener.onFailure(response.code(), response.message());
            }
        });
    }

    public void asyncDownload(@NonNull Request request, @NonNull Callback callback)
    {
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    @Override
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

    @Override
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

    private SSLSocketFactory initSSLSocketFactory()
    {
        SSLSocketFactory ssfFactory = null;
        try
        {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{initX509TrustManager()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ssfFactory;
    }

    @SuppressLint("TrustAllX509TrustManager")
    private X509TrustManager initX509TrustManager()
    {
        return new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
            {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
            {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers()
            {
                return new java.security.cert.X509Certificate[]{ };
            }
        };
    }
}
