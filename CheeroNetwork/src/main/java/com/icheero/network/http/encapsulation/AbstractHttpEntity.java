package com.icheero.network.http.encapsulation;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author 左程耀
 * 封装http请求数据
 */
public abstract class AbstractHttpEntity
{
    private final static char[] MULTIPART_CHARS = "-1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    protected String mBoundary;
    protected HashMap<String, Object> mMap;

    protected AbstractHttpEntity()
    {
        this.mMap = new HashMap<>();
        this.mBoundary = genBoundary();
    }

    public String getBoundary()
    {
        return mBoundary;
    }

    private String genBoundary()
    {
        StringBuilder builder = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 36; i++)
            builder.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        return builder.toString();
    }

    public void addString(String key, String value)
    {
        if (TextUtils.isEmpty(value))
            return;
        mMap.put(key, value);
    }

    public Object get(String key)
    {
        return mMap.get(key);
    }

    public Set<Map.Entry<String, Object>> entrySet()
    {
        return mMap.entrySet();
    }

    public int size()
    {
        return mMap.size();
    }

    public abstract byte[] getBytes() throws IOException;

}
