package com.icheero.sdk.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.PackageUtils;
import com.facebook.stetho.Stetho;
import com.icheero.database.DBHelper;
import com.icheero.network.download.DownloadConfig;
import com.icheero.network.http.HttpConfig;
import com.icheero.sdk.core.manager.ApplicationManager;
import com.icheero.sdk.core.manager.DownloadManager;
import com.icheero.sdk.core.manager.HttpManager;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.util.Log;

public class BaseApplication extends Application
{
    private static final Class TAG = BaseApplication.class;
    private static BaseApplication mInstance;
    private Context mApplicationContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        mApplicationContext = mInstance.getApplicationContext();
        Log.traceMode(Log.TRACE_MODE_ON_SCREEN);
        Log.i(TAG, "Application OnCreate");
        // 初始化 IO管理器
        IOManager.getInstance();
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
        if (ARouter.debuggable() || PackageUtils.isNewVersion(this))
        {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        // 初始化 stetho
        Stetho.initializeWithDefaults(this);
        // 初始化 数据库
        DBHelper.getInstance().init(this);
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        Log.i(TAG, "Application onTerminate");
        ApplicationManager.getInstance().onTerminate(this);
        mInstance = null;
    }

    public static BaseApplication getAppInstance()
    {
        return mInstance;
    }

    public Context getAppicationContext()
    {
        return mApplicationContext;
    }
}
