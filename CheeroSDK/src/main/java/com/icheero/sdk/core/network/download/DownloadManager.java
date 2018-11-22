package com.icheero.sdk.core.network.download;

import android.support.annotation.NonNull;

import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.core.network.okhttp.OkHttpManager;
import com.icheero.sdk.core.network.okhttp.OkHttpRequest;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DownloadManager
{ 
    private final static int THREAD_COUNT_CORE = 3;
    private final static int THREAD_COUNT_MAX = 3;
    private final static int THREAD_ALIVE_TIME = 60;

    private ThreadPoolExecutor mThreadPool;
    private static volatile DownloadManager mInstance;

    private DownloadManager()
    {
        mThreadPool = new ThreadPoolExecutor(THREAD_COUNT_CORE, THREAD_COUNT_MAX, THREAD_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory()
        {
            private AtomicInteger mInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "Download Thread #" + mInteger.getAndIncrement());
            }
        });
    }

    public static DownloadManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (DownloadManager.class)
            {
                if (mInstance == null)
                    mInstance = new DownloadManager();
            }
        }
        return mInstance;
    }

    public void download(String url, @NonNull IDownloadListener listener)
    {
        OkHttpManager.getInstance().asyncDownload(OkHttpRequest.createGetRequest(url), new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                listener.onFailure(OkHttpManager.NETWORK_STATUS_CODE_TIMEOUT, OkHttpManager.NETWORK_ERROR_MSG_TIMEOUT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (!response.isSuccessful())
                    listener.onFailure(OkHttpManager.NETWORK_STATUS_CODE_ERROR, OkHttpManager.NETWORK_ERROR);
                else
                {
                    long contentLength = response.body() != null ? response.body().contentLength() : -1;
                    if (contentLength == -1)
                        listener.onFailure(OkHttpManager.NETWORK_STATUS_CODE_ERROR, OkHttpManager.NETWORK_ERROR_MSG_CONTENT_LENGTH);
                    else
                        processDownload(url, contentLength, listener);
                }
            }
        });
    }

    private void processDownload(String url, long length, IDownloadListener listener)
    {
        // 120 3 40 0~39 40~79 80~119
        long threadDownloadSize = length / THREAD_COUNT_MAX; // 每个线程所需下载的字节数
        for (int i = 0; i < THREAD_COUNT_MAX; i++)
        {
            long start = i * threadDownloadSize;
            long end = (i == THREAD_COUNT_MAX - 1) ? length - 1 : (i + 1) * threadDownloadSize - 1;
            mThreadPool.execute(new DownloadRunnable(url, length, start, end, listener));
        }
    }
}
