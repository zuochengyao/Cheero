package com.icheero.sdk.base;

import android.os.Bundle;
import android.view.View;

import com.icheero.sdk.core.manager.PermissionManager;
import com.icheero.sdk.core.manager.ViewManager;
import com.icheero.sdk.util.Log;

import java.util.Arrays;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        Log.i(TAG, TAG.getSimpleName() + " onCreate");
        mPermissionManager = new PermissionManager(this);
        ViewManager.getInstance().addActivity(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i(TAG, TAG.getSimpleName() + " onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, TAG.getSimpleName() + " onRestoreInstanceState");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ViewManager.getInstance().removeActivity(this);
        Log.i(TAG, TAG.getSimpleName() + " onDestroy");
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
