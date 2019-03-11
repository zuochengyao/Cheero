package com.icheero.sdk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    public static void toast(Context context, int resID, int duration)
    {
        Toast.makeText(context, resID, duration).show();
    }

    public static String getVersionCode(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "1";
        try
        {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                versionCode = packageInfo.getLongVersionCode() + "";
            else
                versionCode = packageInfo.versionCode + "";
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "1.0.0";
        try
        {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
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

    public static Type getGenericInterfaceType(Class interfaceObj)
    {
        Type[] types = interfaceObj.getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        return parameterized.getActualTypeArguments()[0];
    }

    public static Type getGenericClassType(Class classObj)
    {
        Type type = classObj.getGenericSuperclass();
        return type != null ? ((ParameterizedType) type).getActualTypeArguments()[0] : null;
    }

    /**
     * 字节数组 转换 十六进制字符串
     */
    public static String bytes2HexString(byte[] data)
    {
        StringBuilder ret = new StringBuilder();
        for (byte b : data)
        {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1)
                hex = '0' + hex;
            ret.append(hex);
        }
        return ret.toString();
    }

    public static byte[] copyBytes(byte[] res, int start, int count)
    {
        if (res == null)
            return null;
        byte[] result = new byte[count];
        System.arraycopy(res, start, result, 0, count);
        return result;
    }

    public static byte[] int2Byte(int number)
    {
        byte[] data = new byte[4];
        data[0] = (byte) ((number >> 24) & 0xff);
        data[1] = (byte) ((number >> 16) & 0xff);
        data[2] = (byte) ((number >> 8) & 0xff);
        data[3] = (byte) (number & 0xff);
        return data;
    }

    public static int byte2Int(byte[] data)
    {
        return data[3] & 0xFF | (data[2] & 0xFF) << 8 | (data[1] & 0xFF) << 16 | (data[0] & 0xFF) << 24;
    }

    public static short byte2Short(byte[] data)
    {
        return (short) (((data[1] & 0xff) << 8) | (data[0] & 0xff));
    }
}
