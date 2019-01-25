package com.icheero.sdk.base;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.icheero.database.DBHelper;
import com.icheero.network.download.DownloadConfig;
import com.icheero.network.http.HttpConfig;
import com.icheero.sdk.core.manager.AndFixPatchManager;
import com.icheero.sdk.core.manager.DownloadManager;
import com.icheero.sdk.core.manager.HttpManager;
import com.icheero.util.IOManager;
import com.icheero.util.Log;

public class BaseApplication extends Application
{
    private static BaseApplication mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        Log.traceMode(Log.TRACE_MODE_ON_SCREEN);
        // 初始化 IO管理器
        IOManager.getInstance().init(this);
        // 初始化 网络请求
        HttpConfig httpConfig = new HttpConfig.Builder()
                .setConnectTimeout(60)
                .setReadTimeout(30)
                .setWriteTimeout(30)
                .setRetryOnConnectionFailure(true)
                .setHttpClassName(HttpConfig.CLASSNAME_APACHE)
                .build();
        HttpManager.getInstance().init(httpConfig);
        // 初始化 下载管理
        DownloadConfig downloadConfig = new DownloadConfig.Builder()
                .setThreadCoreCount(3)
                .setThreadMaxCount(3)
                .setThreadAliveTime(60)
                .build();
        DownloadManager.getInstance().init(downloadConfig);
        // 初始化 ARouter
        if (BuildConfig.DEBUG)
        {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        // 初始化 AndFix
        AndFixPatchManager.getInstance();
        // 初始化 stetho
        Stetho.initializeWithDefaults(this);
        // 初始化 数据库
        DBHelper.getInstance().init(this);
    }

    public static BaseApplication getAppInstance()
    {
        return mInstance;
    }
}
