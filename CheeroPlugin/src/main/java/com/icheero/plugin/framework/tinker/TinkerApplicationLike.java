package com.icheero.plugin.framework.tinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.icheero.plugin.framework.andfix.AndFixPatchManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

@DefaultLifeCycle(application = ".CheeroTinkerApplication", flags = ShareConstants.TINKER_ENABLE_ALL)
public class TinkerApplicationLike extends ApplicationLike
{
    private Context mContext;

    public TinkerApplicationLike(Application application,
                                 int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime,
                                 long applicationStartMillisTime,
                                 Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base)
    {
        super.onBaseContextAttached(base);
        mContext = base;
        TinkerManager.getInstance().init(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        AndFixPatchManager.getInstance().init(mContext);
    }
}
