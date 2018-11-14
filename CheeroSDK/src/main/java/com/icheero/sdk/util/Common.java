package com.icheero.sdk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.icheero.sdk.base.BaseApplication;

/**
 * Created by 左程耀 on 2018/2/26.
 */

@SuppressWarnings("unused")
public class Common
{
    public static int dp2px(Context context, float dp)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void toast(Context context, CharSequence str, int duration)
    {
        Toast.makeText(context, str, duration).show();
    }

    public static void toast(Context context, @StringRes int resID, int duration)
    {
        Toast.makeText(context, resID, duration).show();
    }

    public static String getVersionCode()
    {
        PackageManager packageManager = BaseApplication.getAppInstance().getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "1";
        try
        {
            packageInfo = packageManager.getPackageInfo(BaseApplication.getAppInstance().getPackageName(), 0);
            versionCode = packageInfo.getLongVersionCode() + "";
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName()
    {
        PackageManager packageManager = BaseApplication.getAppInstance().getPackageManager();
        PackageInfo packageInfo;
        String versionName = "1.0.0";
        try
        {
            packageInfo = packageManager.getPackageInfo(BaseApplication.getAppInstance().getPackageName(), 0);
            versionName = packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 根据url地址 获取文件名
     */
    public static String getFileName(String url)
    {
        int lastSeparatorIndex = url.lastIndexOf("/");
        return (lastSeparatorIndex < 0) ? url : url.substring(lastSeparatorIndex + 1, url.length());
    }

}
