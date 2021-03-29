package com.icheero.sdk.base;

import android.content.res.Configuration;
import android.os.Build;
import android.os.StrictMode;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.icheero.sdk.core.storage.database.DBHelper;
import com.icheero.sdk.core.manager.ApplicationManager;
import com.icheero.sdk.core.manager.DownloadManager;
import com.icheero.sdk.core.manager.HttpManager;
import com.icheero.sdk.core.storage.file.FileScopeManager;
import com.icheero.sdk.core.manager.NotificationManager;
import com.icheero.sdk.core.network.download.DownloadConfig;
import com.icheero.sdk.core.network.http.HttpConfig;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

import androidx.multidex.MultiDexApplication;

public class BaseApplication extends MultiDexApplication
{
    private static final Class<?> TAG = BaseApplication.class;

    private static BaseApplication mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        Log.traceMode(Log.TRACE_MODE_ON_SCREEN);
        Log.i(TAG, TAG.getSimpleName() + " onCreate:" + getApplicationInfo().nativeLibraryDir );
        CheeroNative.nativeIsOwnApp();
        // 初始化 IO管理器
        FileScopeManager.getInstance().init(this);
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
        if (Common.isDebug(this))
        {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        // 初始化 stetho
        Stetho.initializeWithDefaults(this);
        // 初始化 数据库
        DBHelper.getInstance().init(this);
        // 初始化 通知
        NotificationManager.getInstance();
        // 解决 Android 7.0 调用相机传uri时报错
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        Log.i(TAG, "onTerminate");
        ApplicationManager.getInstance().onTerminate(this);
        mInstance = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged");
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        Log.i(TAG, "onTrimMemory level：" + level);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory");
    }

    public static BaseApplication getAppInstance()
    {
        return mInstance;
    }
}
