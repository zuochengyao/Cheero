package com.icheero.sdk.base;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.icheero.sdk.core.manager.AndFixPatchManager;

public class BaseApplication extends Application
{
    private static BaseApplication mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        // 初始化ARouter
        if (BuildConfig.DEBUG)
        {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        // 初始化AndFix
        AndFixPatchManager.getInstance();
    }

    public static BaseApplication getAppInstance()
    {
        return mInstance;
    }
}
