package com.icheero.plugin.application;

import android.app.Application;

import com.icheero.plugin.framework.andfix.AndFixPatchManager;
import com.icheero.sdk.base.ModuleApplication;

public class PluginApplication extends ModuleApplication
{
    @Override
    public void onCreate(Application application)
    {
        super.onCreate(application);
        AndFixPatchManager.getInstance().init(application);
    }
}
