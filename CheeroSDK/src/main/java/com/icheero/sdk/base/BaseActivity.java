package com.icheero.sdk.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.manager.PermissionManager;
import com.icheero.sdk.core.manager.ViewManager;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

import java.util.Arrays;

public class BaseActivity extends AppCompatActivity implements PermissionManager.PermissionListener
{
    protected Class TAG;
    protected PermissionManager mPermissionManager;

    // region Activity's Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TAG = getClass();
        Log.i(TAG, TAG.getSimpleName() + ": onCreate");

        mPermissionManager = new PermissionManager(this);
        ViewManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ViewManager.getInstance().removeActivity(this);
        Log.i(TAG, TAG.getSimpleName() + ": onDestroy");
    }

    // endregion

    // region Extra Methods
    protected <T extends View> T $(@IdRes int resId)
    {
        return super.findViewById(resId);
    }
    // endregion

    // region 权限管理相关
    /**
     * 权限申请回调
     * @param permission 权限名称
     * @param isGranted 权限是否申请成功
     */
    @Override
    public void onPermissionRequest(boolean isGranted, String permission)
    {
        Log.i(TAG, String.format("Permission:%s granted %s ", permission, isGranted));
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            if (isGranted)
                IOManager.getInstance().createRootFolder();
            else
                Common.toast(this, "请打开读写权限！", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 多权限申请回调
     * @param permissions 权限名称列表
     * @param isGranted 所有权限是否申请成功
     */
    @Override
    public void onPermissionRequest(boolean isGranted, String... permissions)
    {
        String permissionsStr = Arrays.asList(permissions).toString();
        Log.i(TAG, String.format("Permission:%s granted %s ", permissionsStr, isGranted));
    }
    // endregion
}
