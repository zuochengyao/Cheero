package com.icheero.sdk.core.network.download;

import android.support.annotation.NonNull;

import com.icheero.sdk.core.database.DBHelper;
import com.icheero.sdk.core.database.entity.Download;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.core.network.okhttp.OkHttpManager;
import com.icheero.sdk.core.network.okhttp.OkHttpRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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
    private ThreadPoolExecutor mThreadPool;
    private HashSet<DownloadTask> mDownloadTaskSet;
    private List<Download> mDownloadCaches;
    private DownloadConfig mDownloadConfig;
    private long mLength;
    private static volatile DownloadManager mInstance;

    private DownloadManager()
    {
        mDownloadTaskSet = new HashSet<>();
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

    public void init(DownloadConfig config)
    {
        this.mDownloadConfig = config;
        mThreadPool = new ThreadPoolExecutor(config.getThreadCoreCount(), config.getThreadMaxCount(), config.getThreadAliveTime(), TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory()
        {
            private AtomicInteger mInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "Download Thread #" + mInteger.getAndIncrement());
            }
        });
    }

    public void download(String url, @NonNull IDownloadListener listener)
    {
        final DownloadTask task = new DownloadTask(url, listener);
        if (mDownloadTaskSet.contains(task))
            listener.onFailure(OkHttpManager.NETWORK_STATUS_CODE_TASK_RUNNING, OkHttpManager.NETWORK_ERROR_MSG_TASK_RUNNING);
        else
        {
            mDownloadTaskSet.add(task);
            mDownloadCaches = DBHelper.getInstance().getAllDownloadByUrl(url);
            if (mDownloadCaches.size() > 0) // mDownloadCaches != null &&
            {
                // 有下载
                for (int i = 0; i < mDownloadCaches.size(); i++)
                {
                    Download entity = mDownloadCaches.get(i);
                    if (i == mDownloadCaches.size() - 1) {
                        mLength = entity.getEnd() + 1;
                    }
                    long startSize = entity.getStart() + entity.getProgress();
                    long endSize = entity.getEnd();
                    mThreadPool.execute(new DownloadRunnable(url, startSize, endSize, entity, listener));
                }
            }
            else
            {
                // 没有下载过
                OkHttpManager.getInstance().asyncDownload(OkHttpRequest.createGetRequest(url), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        listener.onFailure(OkHttpManager.NETWORK_STATUS_CODE_TIMEOUT, OkHttpManager.NETWORK_ERROR_MSG_TIMEOUT);
                        mDownloadTaskSet.remove(task);
                    }

                    @Override
                    public void onResponse(Call call, Response response)
                    {
                        if (!response.isSuccessful())
                            listener.onFailure(OkHttpManager.NETWORK_STATUS_CODE_ERROR, OkHttpManager.NETWORK_ERROR);
                        else
                        {
                            mLength = response.body() != null ? response.body().contentLength() : -1;
                            if (mLength == -1)
                                listener.onFailure(OkHttpManager.NETWORK_STATUS_CODE_ERROR, OkHttpManager.NETWORK_ERROR_MSG_CONTENT_LENGTH);
                            else
                                processDownload(url, mLength, listener);
                        }
                        mDownloadTaskSet.remove(task);
                    }
                });
            }
            onProgressCallback(url, listener);
        }
    }

    private void processDownload(String url, long length, IDownloadListener listener)
    {
        // 120 3 40 0~39 40~79 80~119
        int maxThreadCount = mDownloadConfig.getThreadMaxCount();
        long threadDownloadSize = length / maxThreadCount; // 每个线程所需下载的字节数
        for (int i = 0; i < maxThreadCount; i++)
        {
            long start = i * threadDownloadSize;
            long end = (i == maxThreadCount - 1) ? length - 1 : (i + 1) * threadDownloadSize - 1;
            Download entity = new Download();
            entity.setDownloadUrl(url);
            entity.setStart(start);
            entity.setEnd(end);
            entity.setThreadId(i + 1);
            mThreadPool.execute(new DownloadRunnable(url, start, end, entity, listener));
        }
    }

    private void onProgressCallback(String url, IDownloadListener listener)
    {
        new ProgressThread(url, listener).start();
    }

    /**
     * 插入到数据库中
     * @param entity 实体数据
     */
    void insertToDb(Download entity)
    {
        DBHelper.getInstance().insertDownload(entity);
    }

    /**
     * 下载进度回调线程
     */
    private class ProgressThread extends Thread
    {
        private String mUrl;
        private IDownloadListener mListener;

        public ProgressThread(String url, IDownloadListener listener)
        {
            this.mUrl = url;
            this.mListener = listener;
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    File file = IOManager.getInstance().getCacheFileByName(mUrl);
                    long fileSize = file.length();
                    int progress = (int) (fileSize * 100.0 / mLength);
                    if (progress >= 100)
                    {
                        mListener.onProgress(progress);
                        return;
                    }
                    mListener.onProgress(progress);
                    Thread.sleep(200);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
