package com.icheero.sdk.core.network.http;

public class HttpConfig
{
    /** 连接超时时长 单位：秒 */
    private int mConnectTimeout;
    /** 读 超时时长 单位：秒 */
    private int mReadTimeout;
    /** 写 超时时长 单位：秒 */
    private int mWriteTimeout;
    /** 是否自动重连 */
    private boolean mRetryOnConnectionFailure;

    private HttpConfig(Builder builder)
    {
        this.mConnectTimeout = builder.connectTimeout;
        this.mReadTimeout = builder.readTimeout;
        this.mWriteTimeout = builder.writeTimeout;
        this.mRetryOnConnectionFailure = builder.retryOnConnectionFailure;
    }

    public int getConnectTimeout()
    {
        return mConnectTimeout;
    }

    public int getReadTimeout()
    {
        return mReadTimeout;
    }

    public int getWriteTimeout()
    {
        return mWriteTimeout;
    }

    public boolean isRetryOnConnectionFailure()
    {
        return mRetryOnConnectionFailure;
    }

    public static class Builder
    {
        private int connectTimeout = 60;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private boolean retryOnConnectionFailure = true;

        public Builder setConnectTimeout(int timeout)
        {
            this.connectTimeout = timeout;
            return this;
        }

        public Builder setReadTimeout(int timeout)
        {
            this.readTimeout = timeout;
            return this;
        }

        public Builder setWriteTimeout(int timeout)
        {
            this.writeTimeout = timeout;
            return this;
        }

        public Builder setRetryOnConnectionFailure(boolean retry)
        {
            this.retryOnConnectionFailure = retry;
            return this;
        }

        public HttpConfig build()
        {
            return new HttpConfig(this);
        }
    }
}
