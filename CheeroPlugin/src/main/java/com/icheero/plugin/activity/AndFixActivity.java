package com.icheero.plugin.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.icheero.plugin.R;
import com.icheero.plugin.framework.andfix.AndFixPatchManager;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;


public class AndFixActivity extends BaseActivity
{
    private static final Class TAG = AndFixActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andfix);
        if (!mPermissionManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            mPermissionManager.permissionRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        else
            IOManager.getInstance().createRootFolder();
        initClassLoader();
        $(R.id.create_bug).setOnClickListener(v -> createBug());
        $(R.id.fix_bug).setOnClickListener(v -> fixBug());
    }

    private void initClassLoader()
    {
        ClassLoader classLoader = getClassLoader();
        if (classLoader != null)
        {
            Log.i(TAG, "ClassLoader: " + classLoader.toString());
            while (classLoader.getParent() != null)
            {
                classLoader = classLoader.getParent();
                Log.i(TAG, "ParentClassLoader: " + classLoader.toString());
            }
        }
    }

    private void createBug()
    {
        // adb push /usr/local/apkpatch-1.0.3/outputs/cheero.apatch /storage/emulated/0/Cheero/patches
        Log.print();
    }

    private void fixBug()
    {
        AndFixPatchManager.getInstance().addPatch();
    }

    @Override
    public void onPermissionRequest(boolean isGranted, String permission)
    {
        super.onPermissionRequest(isGranted, permission);
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            if (isGranted)
                IOManager.getInstance().createRootFolder();
            else
                Common.toast(this, "请打开读写权限！", Toast.LENGTH_SHORT);
        }
    }
}
