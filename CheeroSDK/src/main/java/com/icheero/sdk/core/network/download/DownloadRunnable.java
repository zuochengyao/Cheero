package com.icheero.sdk.core.network.download;

import com.icheero.sdk.core.database.entity.Download;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.core.network.okhttp.OkHttpManager;
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
    private long mContentLength;
    private Download mEntity;
    private IDownloadListener mListener;

    DownloadRunnable(String url, long contentLength, long start, long end, Download entity, IDownloadListener listener)
    {
        this.mUrl = url;
        this.mStart = start;
        this.mEnd = end;
        this.mContentLength = contentLength;
        this.mEntity = entity;
        this.mListener = listener;
    }

    @Override
    public void run()
    {
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
                long len;
                long progress = 0;
                InputStream in = response.body().byteStream();
                Log.d(TAG, String.format(Locale.getDefault(), "Thread-%s, from %d bytes to %d bytes, contentLength: %d", Thread.currentThread().getName(), mStart, mEnd, mContentLength));
                while ((len = in.read(buffer, 0, buffer.length)) != -1)
                {
                    randomAccessFile.write(buffer, 0, (int) len);
                    progress += len;
                    mEntity.setProgress(progress);
                    if (mContentLength > 0)
                        mListener.onProgress((int) (file.length() / mContentLength));
                }
                mListener.onSuccess(file);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                DownloadManager.getInstance().insert(mEntity);
            }
        }

    }
}
