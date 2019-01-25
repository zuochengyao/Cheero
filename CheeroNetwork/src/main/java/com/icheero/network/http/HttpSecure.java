package com.icheero.network.http;

import android.annotation.SuppressLint;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
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

    public static SSLConnectionSocketFactory getApacheSocketFactory()
    {
        SSLConnectionSocketFactory ssfFactory = null;
        try
        {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            ssfFactory = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier()
            {

                @Override
                public boolean verify(String arg0, SSLSession arg1)
                {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException
                {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException
                {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException
                {
                }
            });
        }
        catch (GeneralSecurityException e)
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
            public void checkClientTrusted(X509Certificate[] chain, String authType)
            {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
            {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers()
            {
                return new X509Certificate[]{ };
            }
        };
    }

    public static final HostnameVerifier hv = (urlHostName, session) -> {
        System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
        return true;
    };
}
