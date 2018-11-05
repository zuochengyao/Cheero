package com.icheero.common.manager;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.icheero.common.util.Common;

/**
 * @author 左程耀 2018年11月05日
 *
 * 管理AndFix所有的api
 */
public class AndFixPatchManager
{
    private static volatile AndFixPatchManager mInstance;
    private PatchManager mPatchManager;

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
