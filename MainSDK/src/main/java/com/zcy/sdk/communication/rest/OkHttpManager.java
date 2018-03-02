package com.zcy.sdk.communication.rest;

import android.os.Handler;
import android.os.Looper;

import com.zcy.sdk.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static volatile OkHttpManager mInstance;
    private Handler mOkHttpHandler;
    private OkHttpClient mOkHttpClient;

    private OkHttpManager()
    {
        this.mOkHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
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

    public void downloadAsync(final String url, final String destFileDir, final OkHttpUICallback callback) throws IOException
    {
        File file = new File(destFileDir, FileUtils.getFileName(url));
        if (!file.exists())
            file.createNewFile();
        downloadAsync(url, file, callback);
    }

    private void downloadAsync(final String url, final File downFile, final OkHttpUICallback downListener)
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
}
