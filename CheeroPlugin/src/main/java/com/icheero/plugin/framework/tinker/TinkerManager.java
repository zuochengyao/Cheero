package com.icheero.plugin.framework.tinker;

import android.content.Context;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

/**
 * @author 左程耀 2019年01月30日
 *
 * 封装tinker
 */
public class TinkerManager
{
    private static boolean isInstalled = false;

    private Context mContext;
    private ApplicationLike mAppLike;
    private static volatile TinkerManager mInstance;

    private TinkerManager()
    {
    }

    public static TinkerManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (TinkerManager.class)
            {
                if (mInstance == null)
                    mInstance = new TinkerManager();
            }
        }
        return mInstance;
    }

    public void init(ApplicationLike appLike)
    {
        this.mAppLike = appLike;
        if (isInstalled)
            return;
        TinkerInstaller.install(mAppLike);
        mContext = mAppLike.getApplication().getApplicationContext();
        isInstalled = true;
    }

    public void loadPatch(String path)
    {
        if (Tinker.isTinkerInstalled())
            TinkerInstaller.onReceiveUpgradePatch(mContext, path);
    }
}
