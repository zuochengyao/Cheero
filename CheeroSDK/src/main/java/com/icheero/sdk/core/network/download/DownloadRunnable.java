package com.icheero.sdk.core.network.download;

import android.os.Process;

import com.icheero.sdk.core.database.entity.Download;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.core.network.framework.okhttp.OkHttpManager;
import com.icheero.sdk.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Locale;

import okhttp3.Response;

public class DownloadRunnable implements Runnable
{
    private static final Class TAG = DownloadRunnable.class;

    private String mUrl;
    private long mStart;
    private long mEnd;
    private Download mEntity;
    private IDownloadListener mListener;

    DownloadRunnable(String url, long start, long end, Download entity, IDownloadListener listener)
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
        Response response = OkHttpManager.getInstance().syncDownloadByRange(mUrl, mStart, mEnd);
        if (response == null && mListener != null)
            mListener.onFailure(OkHttpManager.NETWORK_STATUS_CODE_ERROR, OkHttpManager.NETWORK_ERROR);
        else
        {
            File file = IOManager.getInstance().getCacheFileByName(mUrl);
            try
            {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(mStart);
                byte[] buffer = new byte[500 * 1024];
                int len;
                long progress = mEntity.getProgress();
                InputStream in = response.body().byteStream();
                Log.d(TAG, String.format(Locale.getDefault(), "Thread-%s, from %d bytes to %d bytes", Thread.currentThread().getName(), mStart, mEnd));
                while ((len = in.read(buffer, 0, buffer.length)) != -1)
                {
                    randomAccessFile.write(buffer, 0, len);
                    progress += len;
                    mEntity.setProgress(progress);
                }
                mListener.onSuccess(file);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                DownloadManager.getInstance().insertToDb(mEntity);
            }
        }

    }
}
