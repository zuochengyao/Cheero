package com.icheero.sdk.core.network.okhttp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.core.network.listener.IResponseListener;
import com.icheero.sdk.util.FileUtils;

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
public class OkHttpManager
{
    private static final Class<OkHttpManager> TAG = OkHttpManager.class;

    private static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private static final int NETWORK_STATUS_CODE_SUCCESS = 200;
    private static final int NETWORK_STATUS_CODE_NOT_FOUND = 404;
    private static final int NETWORK_STATUS_CODE_TIMEOUT = 408;
    private static final int NETWORK_STATUS_CODE_ERROR = 409;

    private static final String NETWORK_ERROR_MSG_NOT_FOUND = "API NOT FOUND";
    private static final String NETWORK_ERROR_MSG_TIMEOUT = "Request TIMEOUT";

    private static volatile OkHttpManager mInstance;
    private Handler mOkHttpHandler;
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
        this.mOkHttpHandler = new Handler(Looper.getMainLooper());
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

    /**
     * 同步请求
     * @param request http request
     * @return response or null
     */
    public Response sync(@NonNull Request request)
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

    /**
     * 异步请求
     * @param request http request
     * @param listener 回调
     */
    public void async(@NonNull Request request, @NonNull IResponseListener listener)
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

    /**
     * 异步请求：下载
     * @param request http request
     * @param listener 下载请求回调
     */
    public void async(@NonNull Request request, @NonNull IDownloadListener listener)
    {
        mOkHttpClient.newCall(request).enqueue(new Callback()
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
                    File file = FileUtils.getFileByUrl(BaseApplication.getAppInstance(), request.url().toString());
                    byte[] buffer = new byte[500 * 1024];
                    int len;
                    int progress = 0;
                    FileOutputStream out = new FileOutputStream(file);
                    InputStream in = response.body().byteStream();
                    while ((len = in.read(buffer, 0, buffer.length)) != -1)
                    {
                        out.write(buffer, 0, len);
                        out.flush();
                        listener.onProgress((int) ((long) (progress += len) / response.body().contentLength()));
                    }
                    listener.onSuccess(file);
                }
            }
        });
    }

    public void downloadAsync(final String url, final String destFileDir, final IResponseListener callback) throws IOException
    {
        File file = new File(destFileDir, FileUtils.getFileName(url));
        if (!file.exists())
            file.createNewFile();
        downloadAsync(url, file, callback);
    }

    private void downloadAsync(final String url, final File downFile, final IResponseListener downListener)
    {
//        OkHttpClient downloadClient = mOkHttpClient.newBuilder().addInterceptor(new Interceptor()
//        {
//            @Override
//            public Response intercept(Chain chain) throws IOException
//            {
//                // 拦截
//                Response origin = chain.proceed(chain.request());
//                // 包装响应体
//                return origin.newBuilder().body(new ProgressBody.ProgressResponseBody(origin.body(), downListener, mOkHttpHandler)).build();
//            }
//        }).build();
//        final Request request = new Request.Builder().url(url).build();
//        downloadClient.newCall(request).enqueue(new OKHttpThreadCallback(mOkHttpHandler, downListener, true).setFile(downFile));
    }

    public byte[] downloadSync(String url)
    {
        byte[] data = null;
        try
        {
            Request request = new Request.Builder().url(url).build();
            Response response = mOkHttpClient.newCall(request).execute();
            data = response.body().bytes();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return data;
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
