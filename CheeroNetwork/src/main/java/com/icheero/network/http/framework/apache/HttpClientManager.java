package com.icheero.network.http.framework.apache;

import com.icheero.network.http.HttpSecure;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HttpClientManager
{
    private static volatile HttpClientManager mInstance;
    private RequestConfig.Builder mBuilder;

    private HttpClientManager()
    {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
        //持续握手
        HttpProtocolParams.setUseExpectContinue(httpParams, true);
        mBuilder = RequestConfig.custom();
        // mBuilder.setProxy(new HttpHost("10.155.2.130", 8880));
    }

    public static HttpClientManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpClientManager.class)
            {
                if (mInstance == null)
                    mInstance = new HttpClientManager();
            }
        }
        return mInstance;
    }

    /**
     * 等待服务器响应 timeout
     * @param readTimeout second
     */
    void setSoTimeout(int readTimeout)
    {
        mBuilder.setSocketTimeout(readTimeout * 1000);
    }

    /**
     * 建立连接 & 获取连接池连接 timeout
     * @param connectTimeout second
     */
    void setConnectTimeout(int connectTimeout)
    {
        mBuilder.setSocketTimeout(connectTimeout * 1000).setConnectionRequestTimeout(connectTimeout * 1000);
    }

    HttpClient newHttpClient()
    {
        return HttpClientBuilder
                .create()
                .setSSLSocketFactory(HttpSecure.getApacheSocketFactory())
                .setDefaultRequestConfig(mBuilder.build())
                .build();
    }
}
