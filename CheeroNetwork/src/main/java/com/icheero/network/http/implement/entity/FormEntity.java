package com.icheero.network.http.implement.entity;

import com.icheero.network.http.HttpApi;
import com.icheero.network.http.encapsulation.AbstractHttpEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


public class FormEntity extends AbstractHttpEntity
{
    @Override
    public byte[] getBytes()
    {
        if (mMap.size() > 0)
        {
            StringBuilder sb = new StringBuilder();
            int index = 0;
            for (Map.Entry<String, Object> entry : mMap.entrySet())
            {
                try
                {
                    sb.append(URLEncoder.encode(entry.getKey(), HttpApi.ENCODING_UTF8))
                      .append("=")
                      .append(URLEncoder.encode(entry.getValue().toString(), HttpApi.ENCODING_UTF8));
                    if (index != mMap.size() - 1)
                        sb.append("&");
                    index++;
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
            return sb.toString().getBytes();
        }
        return null;
    }
}
