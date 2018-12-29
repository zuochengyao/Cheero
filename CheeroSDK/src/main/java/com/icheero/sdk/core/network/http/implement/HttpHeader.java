package com.icheero.sdk.core.network.http.implement;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 左程耀
 *
 * 封装Http请求头
 */
public class HttpHeader implements Map<String, String>
{
    public final static String HEADER_ACCEPT = "Accept";
    public final static String HEADER_PRAGMA = "Pragma";
    public final static String HEADER_PROXY_CONNECTION = "Proxy-Connection";
    public final static String HEADER_USER_AGENT = "User-Agent";
    public final static String HEADER_ACCEPT_ENCODING = "accept-encoding";
    public final static String HEADER_CACHE_CONTROL = "Cache-Control";
    public final static String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public final static String HEADER_CONNECTION = "Connection";
    public final static String HEADER_CONTENT_LENGTH = "Content-length";
    public final static String HEADER_CONTENT_TYPE = "Content-Type";

    private Map<String, String> mMap;

    public HttpHeader()
    {
        mMap = new HashMap<>();
    }

    // region Getter and Setter for IHttpHeader

    public String getAccept()
    {
        return get(HEADER_ACCEPT);
    }

    public void setAccept(String value)
    {
        put(HEADER_ACCEPT, value);
    }

    public String getPragma()
    {
        return get(HEADER_PRAGMA);
    }

    public void setPragma(String value)
    {
        put(HEADER_PRAGMA, value);
    }

    public String getUserAgent()
    {
        return get(HEADER_USER_AGENT);
    }

    public void setUserAgent(String value)
    {
        put(HEADER_USER_AGENT, value);
    }

    public String getProxyConnection()
    {
        return get(HEADER_PROXY_CONNECTION);
    }

    public void setProxyConnection(String value)
    {
        put(HEADER_PROXY_CONNECTION, value);
    }

    public String getAcceptEncoding()
    {
        return get(HEADER_ACCEPT_ENCODING);
    }

    public void setAcceptEncoding(String value)
    {
        put(HEADER_ACCEPT_ENCODING, value);
    }

    public String getCacheControl()
    {
        return get(HEADER_CACHE_CONTROL);
    }

    public void setCacheControl(String value)
    {
        put(HEADER_CACHE_CONTROL, value);
    }

    public String getContentEncoding()
    {
        return get(HEADER_CONTENT_ENCODING);
    }

    public void setContentEncoding(String value)
    {
        put(HEADER_CONTENT_ENCODING, value);
    }

    public String getConnection()
    {
        return get(HEADER_CONNECTION);
    }

    public void setConnection(String value)
    {
        put(HEADER_CONNECTION, value);
    }

    public String getContentLength()
    {
        return get(HEADER_CONTENT_LENGTH);
    }

    public void setContentLength(String value)
    {
        put(HEADER_CONTENT_LENGTH, value);
    }

    public String getContentType()
    {
        return get(HEADER_CONTENT_TYPE);
    }

    public void setContentType(String value)
    {
        put(HEADER_CONTENT_TYPE, value);
    }

    // endregion

    // region implements Map
    @Override
    public int size()
    {
        return mMap.size();
    }

    @Override
    public boolean isEmpty()
    {
        return mMap.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object key)
    {
        return mMap.containsValue(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value)
    {
        return mMap.containsValue(value);
    }

    @Nullable
    @Override
    public String get(@Nullable Object key)
    {
        return mMap.get(key);
    }

    @Nullable
    @Override
    public String put(@NonNull String key, @NonNull String value)
    {
        return mMap.put(key, value);
    }

    @Nullable
    @Override
    public String remove(@Nullable Object key)
    {
        return mMap.remove(key);
    }

    @Override
    public void putAll(@NonNull Map<? extends String, ? extends String> m)
    {
        mMap.putAll(m);
    }

    @Override
    public void clear()
    {
        mMap.clear();
    }

    @NonNull
    @Override
    public Set<String> keySet()
    {
        return mMap.keySet();
    }

    @NonNull
    @Override
    public Collection<String> values()
    {
        return mMap.values();
    }

    @NonNull
    @Override
    public Set<Entry<String, String>> entrySet()
    {
        return mMap.entrySet();
    }

    // endregion
}
