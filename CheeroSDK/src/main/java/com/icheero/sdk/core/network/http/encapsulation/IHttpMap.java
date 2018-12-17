package com.icheero.sdk.core.network.http.encapsulation;

import java.util.Map;

/**
 * @author 左程耀
 *
 * 封装http请求数据 Map
 */
public interface IHttpMap<K, V> extends Map<K, V>
{
     V get(String key);

     void set(String key, V value);

     void setAll(Map<String, V> map);
}
