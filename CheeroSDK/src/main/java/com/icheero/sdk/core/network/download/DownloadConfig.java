package com.icheero.sdk.core.network.download;

public class DownloadConfig
{
    private final static int THREAD_COUNT_CORE = 3;
    private final static int THREAD_COUNT_MAX = 3;
    private final static int THREAD_ALIVE_TIME = 60;

    private int mThreadCoreCount;
    private int mThreadMaxCount;
    private int mThreadAliveTime;

    private DownloadConfig(Builder builder)
    {
        this.mThreadCoreCount = builder.threadCoreCount;
        this.mThreadMaxCount = builder.threadMaxCount;
        this.mThreadAliveTime = builder.threadAliveTime;
    }

    int getThreadCoreCount()
    {
        return mThreadCoreCount;
    }

    int getThreadMaxCount()
    {
        return mThreadMaxCount;
    }

    int getThreadAliveTime()
    {
        return mThreadAliveTime;
    }

    public static class Builder
    {
        private int threadCoreCount;
        private int threadMaxCount;
        private int threadAliveTime;

        public Builder setThreadCoreCount(int count)
        {
            this.threadCoreCount = count > 0 ? count : THREAD_COUNT_CORE;
            return this;
        }

        public Builder setThreadMaxCount(int count)
        {
            this.threadMaxCount = count > 0 ? count : THREAD_COUNT_MAX;
            return this;
        }

        /**
         * 单位 秒
         */
        public Builder setThreadAliveTime(int time)
        {
            this.threadAliveTime = time > 0 ? time : THREAD_ALIVE_TIME;
            return this;
        }

        public DownloadConfig build()
        {
            return new DownloadConfig(this);
        }
    }
}
