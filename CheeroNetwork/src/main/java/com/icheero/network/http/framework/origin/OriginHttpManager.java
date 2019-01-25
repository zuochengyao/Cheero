package com.icheero.network.http.framework.origin;

import com.icheero.network.http.HttpSecure;

import javax.net.ssl.HttpsURLConnection;

public class OriginHttpManager
{
    private static final Class TAG = OriginHttpManager.class;

    private static volatile OriginHttpManager mInstance;

    private OriginHttpManager()
    {
        ignoreSsl();
    }

    public static OriginHttpManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OriginHttpManager.class)
            {
                if (mInstance == null)
                    mInstance = new OriginHttpManager();
            }
        }
        return mInstance;
    }

    /**
     * 忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     */
    private void ignoreSsl()
    {
        HttpsURLConnection.setDefaultSSLSocketFactory(HttpSecure.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(HttpSecure.hv);
    }
}
