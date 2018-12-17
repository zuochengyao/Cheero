package com.icheero.sdk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import com.icheero.sdk.base.BaseApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
     * 生成一个MD5加密串
     * @param str 待加密串
     */
    public static String md5(String str)
    {
        StringBuilder builder = new StringBuilder();
        if (TextUtils.isEmpty(str)) return null;
        try
        {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(str.getBytes());
            byte[] cipher = digest.digest();

            for (byte b : cipher)
            {
                String hexStr = Integer.toHexString(b & 0xff);
                builder.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static boolean isClassExist(String className, ClassLoader loader)
    {
        boolean flag = true;
        try
        {
            Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            flag = false;
        }
        return flag;
    }

}
