package com.icheero.sdk.core.network.http;

import android.annotation.SuppressLint;

import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpSecure
{
    public static SSLSocketFactory getSocketFactory()
    {
        SSLSocketFactory ssfFactory = null;
        try
        {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{initX509TrustManager()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    @SuppressLint("TrustAllX509TrustManager")
    public static X509TrustManager initX509TrustManager()
    {
        return new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
            {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
            {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers()
            {
                return new java.security.cert.X509Certificate[]{ };
            }
        };
    }

    public static final HostnameVerifier hv = (urlHostName, session) -> {
        System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
        return true;
    };
}
