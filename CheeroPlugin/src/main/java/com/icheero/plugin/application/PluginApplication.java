package com.icheero.plugin.application;

import android.app.Application;

import com.icheero.plugin.hotfix.andfix.AndFixPatchManager;
import com.icheero.sdk.base.ModuleApplication;

public class PluginApplication extends ModuleApplication
{
    @Override
    public void onCreate(Application application)
    {
        super.onCreate(application);
        AndFixPatchManager.getInstance().init(application);
        try
        {
//            GlobalActivityHookHelper.hook();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
