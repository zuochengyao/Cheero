package com.icheero.plugins.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.icheero.plugins.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.AndFixPatchManager;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

public class MainActivity extends BaseActivity
{
    private static final Class TAG = MainActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.click_me).setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Hello plugin!", Toast.LENGTH_SHORT).show();
            initClassLoader();
        });
        if (!mPermissionManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            mPermissionManager.permissionRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        else
            IOManager.getInstance().createRootFolder();
        findViewById(R.id.create_bug).setOnClickListener(v -> createBug());

        findViewById(R.id.fix_bug).setOnClickListener(v -> fixBug());
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
