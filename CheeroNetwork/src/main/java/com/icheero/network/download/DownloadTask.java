package com.icheero.network.download;

import com.icheero.network.listener.IDownloadListener;

import java.util.Objects;

public class DownloadTask
{
    private String mUrl;
    private IDownloadListener mListener;

    public DownloadTask(String url, IDownloadListener listener)
    {
        this.mUrl = url;
        this.mListener = listener;
    }

    public String getUrl()
    {
        return mUrl;
    }

    public void setUrl(String url)
    {
        this.mUrl = url;
    }

    public IDownloadListener getListener()
    {
        return mListener;
    }

    public void setListener(IDownloadListener listener)
    {
        this.mListener = listener;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadTask that = (DownloadTask) o;
        return Objects.equals(mUrl, that.mUrl) && Objects.equals(mListener, that.mListener);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(mUrl, mListener);
    }
}
