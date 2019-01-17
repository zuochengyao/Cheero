package com.icheero.sdk.core.database.service;

import com.icheero.sdk.core.database.entity.Download;

import java.util.List;

public interface IDownloadService
{
    void insertDownload(Download entity);

    void updateDownload(Download entity);

    List<Download> getAllDownloadByUrl(String url);
}
