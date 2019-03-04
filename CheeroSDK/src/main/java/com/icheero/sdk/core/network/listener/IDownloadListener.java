package com.icheero.sdk.core.network.listener;

import java.io.File;

/**
 * @author 左程耀 2018年11月19日
 *
 * 下载监听器
 */
public interface IDownloadListener extends IBaseListener
{
    void onSuccess(File downloadFile);

    void onProgress(int progress);
}
