package com.icheero.sdk.core.manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.icheero.sdk.base.BaseApplication;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * @author 左程耀 2018-11-14
 * 权限管理器
 */
@SuppressLint("CheckResult")
public class PermissionManager
{
    private AppCompatActivity mActivity;
    private RxPermissions mRxPermissions;
    private PermissionListener mListener;

    public PermissionManager(@NonNull AppCompatActivity activity)
    {
        this.mActivity = activity;
        this.mRxPermissions = new RxPermissions(mActivity);
        if (activity instanceof PermissionListener)
            this.mListener = (PermissionListener) activity;
    }

    /**
     * 检测单个权限是否已授权
     * @param permission 权限名称
     */
    public boolean checkPermission(final String permission)
    {
        return isGranted(permission);
    }

    /**
     * 检查多个权限是否都已授权
     * @param permissions 多个权限名称
     */
    public boolean checkPermission(final String... permissions)
    {
        // 如果小于23，则默认通过所有权限
        if (!checkAndroidVersionCode())
            return true;
        for (String permission : permissions)
        {
            if (!isGranted(permission))
                return false;
        }
        return true;
    }

    /**
     * 获取app中所需的权限
     */
    public String[] getAppPermissions()
    {
        PackageManager packageManager = mActivity.getPackageManager();
        String packageName = mActivity.getPackageName();
        String[] permissions = null;
        try
        {
            permissions = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return permissions;
    }

    /**
     * 申请一个权限
     * @param permission 权限名称
     */
    public void permissionRequest(final String permission)
    {
        if (checkAndroidVersionCode())
            mRxPermissions.request(permission).subscribe(granted -> {
                if (mListener != null)
                    mListener.onPermissionRequest(granted, permission);
            });
    }

    /**
     * 申请多个权限，只要有一个失败就执行失败回调
     * @param permissions 多个权限名称
     */
    public void permissionRequest(final String... permissions)
    {
        if (checkAndroidVersionCode())
        {
            mRxPermissions.request(permissions).subscribe(granted -> {
                if (mListener != null)
                    mListener.onPermissionRequest(granted, permissions);
            });
        }
    }

    /**
     * 申请多个权限，单独执行失败回调
     * @param permissions
     */
    public void permissionRequestEach(final String... permissions)
    {
        if (checkAndroidVersionCode())
        {
            mRxPermissions.requestEach(permissions).subscribe(permission -> {
                if (mListener != null)
                {
                    if (permission.shouldShowRequestPermissionRationale)
                    {
                        launchSystemAppDetailsActivity();
                    }
                    else
                        mListener.onPermissionRequest(permission.granted, permission.name);
                }
            });
        }
    }

    private boolean checkAndroidVersionCode()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private boolean isGranted(final String permission)
    {
        return !checkAndroidVersionCode() || PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this.mActivity, permission);
    }

    private void launchSystemAppDetailsActivity()
    {
        Intent permissionIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        permissionIntent.setData(Uri.parse("package:" + BaseApplication.getAppInstance().getPackageName()));
        BaseApplication.getAppInstance().startActivity(permissionIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public interface PermissionListener
    {
        void onPermissionRequest(boolean isGranted, String permissions);

        void onPermissionRequest(boolean isGranted, String... permissions);
    }
}
