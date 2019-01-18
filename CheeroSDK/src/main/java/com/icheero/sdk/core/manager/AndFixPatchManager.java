package com.icheero.sdk.core.manager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.util.Common;

/**
 * @author 左程耀 2018年11月05日
 *
 * 管理AndFix所有的api
 */
public class AndFixPatchManager
{
    public static final String PATCH_EXTENSION = ".apatch";

    private static volatile AndFixPatchManager mInstance;
    private PatchManager mPatchManager;

    private AndFixPatchManager()
    {
        mPatchManager = new PatchManager(BaseApplication.getAppInstance());
        mPatchManager.init(Common.getVersionName());
        mPatchManager.loadPatch();
    }

    public static AndFixPatchManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (AndFixPatchManager.class)
            {
                if (mInstance == null)
                    mInstance = new AndFixPatchManager();
            }
        }
        return mInstance;
    }

    public void addPatch(String path)
    {
        try
        {
            if (mPatchManager != null)
                mPatchManager.addPatch(path);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
