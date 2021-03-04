package com.icheero.plugin.hotfix.andfix;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.icheero.sdk.core.manager.FileManager;
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
    { }

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

    public void init(Context context)
    {
        mPatchManager = new PatchManager(context);
        mPatchManager.init(Common.getVersionName(context));
        mPatchManager.loadPatch();
    }

    public void addPatch()
    {
        try
        {
            if (mPatchManager != null)
                mPatchManager.addPatch(FileManager.DIR_FILES_APATCH.concat("/cheero").concat(AndFixPatchManager.PATCH_EXTENSION));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
