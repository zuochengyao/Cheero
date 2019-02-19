package com.icheero.faceid.application;

import android.app.Application;

import com.icheero.faceid.manager.FaceIDManager;
import com.icheero.sdk.base.ModuleApplication;
import com.icheero.util.Log;

public class FaceIDApplication extends ModuleApplication
{
    @Override
    public void onCreate(Application application)
    {
        Log.i(TAG, "onCreate");
        FaceIDManager.getInstance();
    }
}
