package com.icheero.sdk.core.manager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.util.Common;

import java.io.File;

/**
 * @author 左程耀 2018年11月05日
 *
 * 管理AndFix所有的api
 */
public class AndFixPatchManager
{
    public static final String PATCH_EXTENSION = ".apatch";
    private String mPatchDir; // patch文件路径

    private static volatile AndFixPatchManager mInstance;
    private PatchManager mPatchManager;

    public String getPatchDir()
    {
        return mPatchDir;
    }

    private AndFixPatchManager()
    {
        mPatchManager = new PatchManager(BaseApplication.getAppInstance());
        mPatchManager.init(Common.getVersionName());
        mPatchManager.loadPatch();
        mPatchDir = BaseApplication.getAppInstance().getExternalCacheDir().getAbsolutePath() + "/apatch/";
        File file = new File(mPatchDir);
        if (!file.exists())
            file.mkdir();
    }

    public static AndFixPatchManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (AndFixPatchManager.class)
            {
                if (mInstance == null) mInstance = new AndFixPatchManager();
            }
        }
        return mInstance;
    }

    public void addPatch(String path)
    {
        try
        {
            if (mPatchManager != null)
            {
                mPatchManager.addPatch(path);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
