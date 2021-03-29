package com.icheero.sdk.core.network.http.framework.okhttp;

import com.icheero.sdk.core.storage.file.FileScopeManager;
import com.icheero.sdk.core.network.http.HttpSecure;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.util.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp 工具类
 * Created by zuochengyao on 2018/3/1.
 */
public class OkHttpManager {
    private static final Class<OkHttpManager> TAG = OkHttpManager.class;

    private static volatile OkHttpManager mInstance;
    private OkHttpClient.Builder mBuilder;

    private OkHttpManager() {
        this.mBuilder = new OkHttpClient.Builder().hostnameVerifier(HttpSecure.hv)
                .sslSocketFactory(HttpSecure.getSocketFactory(), HttpSecure.initX509TrustManager()); // 支持https
    }

    public static OkHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (TAG) {
                if (mInstance == null)
                    mInstance = new OkHttpManager();
            }
        }
        return mInstance;
    }

    OkHttpClient getOkHttpClient() {
        return mBuilder.build();
    }

    public void setReadTimeout(int readTimeout) {
        this.mBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
    }

    void setWriteTimeout(int writeTimeout) {
        this.mBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
    }

    void setConnectionTimeout(int connectionTimeout) {
        this.mBuilder.connectTimeout(connectionTimeout, TimeUnit.SECONDS);
    }

    void setRetryOnConnectionFailure(boolean retry) {
        this.mBuilder.retryOnConnectionFailure(retry).build();
    }

    public Response syncRequest(@NonNull Request request) {
        try {
            return getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response syncDownload(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            return getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void asyncDownload(@NonNull Request request, @NonNull Callback callback) {
        getOkHttpClient().newCall(request).enqueue(callback);
    }

    public void asyncDownload(@NonNull Request request, @NonNull IDownloadListener listener) {
        asyncDownload(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(408, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())
                    listener.onFailure(response.code(), response.message());
                else {
                    File file = FileScopeManager.getInstance().createDownloadFile(Common.md5(request.url().toString()));
                    byte[] buffer = new byte[500 * 1024];
                    int len;
                    int progress = 0;
                    long contentLength = response.body().contentLength();
                    FileOutputStream out = new FileOutputStream(file);
                    InputStream in = response.body().byteStream();

                    while ((len = in.read(buffer, 0, buffer.length)) != -1) {
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

    public Response syncDownloadByRange(String url, long start, long end) {
        Request request = new Request.Builder().url(url).addHeader("Range", "bytes=" + start + "-" + end).build();
        try {
            return getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Request createGetRequest(String url) {
        return new Request.Builder().url(url).build();
    }
}
