package com.icheero.sdk.core.network;

import android.support.annotation.NonNull;

import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.core.network.listener.IResponseListener;

import okhttp3.Request;
import okhttp3.Response;

public interface IHttpManager
{
    /**
     * http(s) 同步请求
     * @param request 请求体
     * @return 响应体 or NULL
     */
    Response syncRequest(@NonNull Request request);

    /**
     * 同步下载
     * @param url 下载资源地址
     * @return 响应体 or NULL
     */
    Response syncDownload(String url);

    /**
     * 同步下载 分段
     * @param url 下载资源地址
     * @param start 起始位置
     * @param end 结束位置
     * @return 响应体 or NULL
     */
    Response syncDownloadByRange(String url, long start, long end);

    /**
     * http(s) 异步请求
     * @param request 请求体
     * @param listener 回调监听器
     */
    void asyncRequest(@NonNull Request request, IResponseListener listener);

    /**
     * 异步请求：下载
     * @param request http 请求体
     * @param listener 下载请求回调
     */
    void asyncDownload(@NonNull Request request, IDownloadListener listener);


}
