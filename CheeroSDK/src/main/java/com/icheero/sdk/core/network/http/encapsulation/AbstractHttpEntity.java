package com.icheero.sdk.core.network.http.encapsulation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 左程耀
 * 封装http请求数据
 */
public abstract class AbstractHttpEntity<T>
{
    protected HashMap<String, T> mMap = new HashMap<>();

    public void addString(String key, String value)
    {
        mMap.put(key, (T) value);
    }

    public abstract Set<Map.Entry<String, T>> entrySet();

    public abstract byte[] getBytes();

    public abstract void write(OutputStream out) throws IOException;
}
