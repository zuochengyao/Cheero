package com.icheero.sdk.core.network.download;

import android.os.Process;

import com.icheero.sdk.core.database.DBHelper;
import com.icheero.sdk.core.database.entity.Download;
import com.icheero.sdk.core.network.http.HttpRequest;
import com.icheero.sdk.core.network.http.HttpRequestProvider;
import com.icheero.sdk.core.network.http.encapsulation.HttpMethod;
import com.icheero.sdk.core.network.http.encapsulation.HttpStatus;
import com.icheero.sdk.core.network.http.encapsulation.IHttpResponse;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Locale;

public class DownloadRunnable implements Runnable
{
    private static final Class TAG = DownloadRunnable.class;

    private String mUrl;
    private long mStart;
    private long mEnd;
    private Download mEntity;
    private IDownloadListener mListener;

    public DownloadRunnable(String url, long start, long end, Download entity, IDownloadListener listener)
    {
        this.mUrl = url;
        this.mStart = start;
        this.mEnd = end;
        this.mEntity = entity;
        this.mListener = listener;
    }

    @Override
    public void run()
    {
        // 后台优先级
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        HttpRequest request = new HttpRequest();
        request.setUrl(mUrl);
        request.setMethod(HttpMethod.GET);
        request.getHeader().put("Range", "bytes=" + mStart + "-" + mEnd);
        try
        {
            IHttpResponse httpResponse = HttpRequestProvider.getInstance().getHttpCall(request).execute();
            if (httpResponse == null)
            {
                if (mListener != null)
                    mListener.onFailure(HttpStatus.NETWORK_ERROR.getStatusCode(), HttpStatus.NETWORK_ERROR.getMessage());
            }
            else
            {
                DBHelper.getInstance().insertDownload(mEntity);
                File file = FileUtils.createFile(FileUtils.DIR_PATH_CHEERO_CACHE + Common.md5(mEntity.getDownloadUrl()));
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(mStart);
                byte[] buffer = new byte[500 * 1024];
                int len;
                long progress = mEntity.getProgress();
                InputStream in = httpResponse.getBody();
                Log.d(TAG, String.format(Locale.getDefault(), "Thread-%s, from %d bytes to %d bytes", Thread.currentThread().getName(), mStart, mEnd));
                while ((len = in.read(buffer, 0, buffer.length)) != -1)
                {
                    randomAccessFile.write(buffer, 0, len);
                    progress += len;
                    mEntity.setProgress(progress);
                    DBHelper.getInstance().updateDownload(mEntity);
                }
            }
        }
        catch (IOException e)
        {
            // TODO
            // DownloadManager.getInstance().stopProgress();
        }
    }
}
