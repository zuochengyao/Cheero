package com.zcy.sdk.communication;

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

    private OkHttpClient mOkHttpClient;

    private static volatile OkHttpManager mInstance;

    private OkHttpManager()
    {
        this.mOkHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
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

    public byte[] fileDownload(String url)
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
