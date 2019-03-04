package com.icheero.faceid.application;

import android.app.Application;

import com.icheero.faceid.manager.FaceIDManager;
import com.icheero.sdk.base.ModuleApplication;

public class FaceIDApplication extends ModuleApplication
{
    @Override
    public void onCreate(Application application)
    {
        FaceIDManager.getInstance();
    }
}
