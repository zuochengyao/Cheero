package com.icheero.sdk.core.network.http;

public class HttpConfig
{
    /** 连接超时时长 单位：秒 */
    private int mConnectTimeout;
    /** 读 超时时长 单位：秒 */
    private int mReadTimeout;
    /** 写 超时时长 单位：秒 */
    private int mWriteTimeout;

    private HttpConfig(Builder builder)
    {
        this.mConnectTimeout = builder.connectTimeout;
        this.mReadTimeout = builder.readTimeout;
        this.mWriteTimeout = builder.writeTimeout;
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

    public static class Builder
    {
        private int connectTimeout = 60;
        private int readTimeout = 30;
        private int writeTimeout = 30;

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

        public HttpConfig build()
        {
            return new HttpConfig(this);
        }
    }
}
