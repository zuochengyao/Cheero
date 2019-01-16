package com.icheero.sdk.core.manager;

import android.support.annotation.NonNull;

import com.icheero.sdk.core.database.DBHelper;
import com.icheero.sdk.core.database.entity.Download;
import com.icheero.sdk.core.network.download.DownloadConfig;
import com.icheero.sdk.core.network.download.DownloadRunnable;
import com.icheero.sdk.core.network.download.DownloadTask;
import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.HttpRequestProvider;
import com.icheero.sdk.core.network.http.HttpResponse;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.core.network.listener.IResponseListener;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
            listener.onFailure(HttpStatus.TASK_RUNNING.getStatusCode(), HttpStatus.TASK_RUNNING.getMessage());
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
                    if (i == mDownloadCaches.size() - 1)
                        mLength = entity.getEnd() + 1;
                    long startSize = entity.getStart() + entity.getProgress();
                    long endSize = entity.getEnd();
                    mThreadPool.execute(new DownloadRunnable(url, startSize, endSize, entity, listener));
                }
            }
            else // 没有下载过
            {
                try
                {
                    HttpRequest request = new HttpRequest();
                    request.setUrl(url);
                    request.setMethod(HttpMethod.GET);
                    request.setResponse(new HttpResponse(new IResponseListener<String>()
                    {
                        @Override
                        public void onSuccess(String data)
                        {
                            mLength = Long.parseLong(data);
                            if (mLength == -1)
                                listener.onFailure(HttpStatus.CONTENT_LENGTH.getStatusCode(), HttpStatus.CONTENT_LENGTH.getMessage());
                            else
                                processDownload(url, mLength, listener);
                            mDownloadTaskSet.remove(task);
                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage)
                        {
                            listener.onFailure(errorCode, errorMessage);
                            mDownloadTaskSet.remove(task);
                        }
                    }, null));
                    HttpRequestProvider.getInstance().getHttpCall(request).download();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
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
    public void insertToDb(Download entity)
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

        ProgressThread(String url, IDownloadListener listener)
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
