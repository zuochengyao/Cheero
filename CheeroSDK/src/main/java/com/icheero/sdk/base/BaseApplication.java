package com.icheero.sdk.base;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.icheero.sdk.manager.AndFixPatchManager;

public class BaseApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        // 初始化ARouter
        if (BuildConfig.DEBUG)
        {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        // 初始化AndFix
        AndFixPatchManager.getInstance().init(this);
    }

}
