package com.icheero.sdk.manager;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
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

    /**
     * 初始化AndFix中的PatchManager
     */
    public void init(Context context)
    {
        mPatchManager = new PatchManager(context.getApplicationContext());
        mPatchManager.init(Common.getVersionName(context));
        mPatchManager.loadPatch();
        mPatchDir = context.getExternalCacheDir().getAbsolutePath() + "/apatch/";
        File file = new File(mPatchDir);
        if (!file.exists())
        {
            file.mkdir();
        }
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
