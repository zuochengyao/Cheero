package com.icheero.sdk.core.network.http.framework.apache;

import org.apache.http.HttpVersion;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HttpClientManager
{
    private HttpParams mHttpParams = new BasicHttpParams();
    private static volatile HttpClientManager mInstance;

    private HttpClientManager()
    {
        HttpConnectionParams.setTcpNoDelay(mHttpParams, true);
        HttpProtocolParams.setVersion(mHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(mHttpParams, HTTP.UTF_8);
        //持续握手
        HttpProtocolParams.setUseExpectContinue(mHttpParams, true);
        // RequestConfig config = RequestConfig.custom().setSocketTimeout(1).build();
    }

    public static HttpClientManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpClientManager.class)
            {
                if (mInstance == null) mInstance = new HttpClientManager();
            }
        }
        return mInstance;
    }

    void setSoTimeout(int readTimeout)
    {
        HttpConnectionParams.setSoTimeout(mHttpParams, readTimeout * 1000);
    }

    void setConnectTimeout(int connectTimeout)
    {
        HttpConnectionParams.setConnectionTimeout(mHttpParams, connectTimeout * 1000);
    }
}
