package com.icheero.sdk.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.icheero.sdk.core.manager.PermissionManager;
import com.icheero.sdk.core.manager.ViewManager;
import com.icheero.sdk.util.Log;
import com.icheero.sdk.util.RefUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements PermissionManager.PermissionListener
{
    protected Class<? extends BaseActivity> TAG;
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
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, TAG.getSimpleName() + " onStart");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i(TAG, TAG.getSimpleName() + " onSaveInstanceState");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(TAG, TAG.getSimpleName() + " onResume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(TAG, TAG.getSimpleName() + " onPause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(TAG, TAG.getSimpleName() + " onStop");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.i(TAG, TAG.getSimpleName() + " onRestart");
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, TAG.getSimpleName() + " onRestoreInstanceState");
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

    protected void openActivity(Class<? extends Activity> activityClass)
    {
        if (activityClass != null)
            openActivity(activityClass, null);
    }

    protected void openActivity(Class<? extends Activity> activityClass, Bundle bundle)
    {
        Intent _Intent = new Intent();
        _Intent.setClass(this, activityClass);
        if (bundle != null && bundle.size() > 0)
            _Intent.putExtras(bundle);
        startActivity(_Intent);
    }

    protected final int getStatusBarHeight()
    {
        int resId, startBarHeight = 0;
        try
        {
            Object obj = RefUtils.getFieldObject("com.android.internal.R$dimen", "status_bar_height");
            if (obj == null)
                return 0;
            resId = Integer.parseInt(obj.toString());
            startBarHeight = getResources().getDimensionPixelSize(resId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return startBarHeight;
    }

    protected final int getNavBarHeight()
    {
        int resId = getResources().getIdentifier("navigation_bar_height","dimen", "android");
        return getResources().getDimensionPixelSize(resId);
    }

    protected final boolean isPortrait()
    {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
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
