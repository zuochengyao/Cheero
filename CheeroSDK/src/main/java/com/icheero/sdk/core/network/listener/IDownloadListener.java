package com.icheero.sdk.core.network.listener;

import java.io.File;

/**
 * @author 左程耀 2018年11月19日
 *
 * 下载监听器
 */
public interface IDownloadListener
{
    void onSuccess(File downloadFile);

    void onFailure(int errorCode, String errorMsg);

    void onProgress(int progress);
}
