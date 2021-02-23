package com.icheero.sdk.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.icheero.sdk.base.BaseApplication;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by 左程耀 on 2018/2/26.
 */

@SuppressWarnings("unused")
public class Common
{
    /**
     * 获取最大公因数
     */
    public static int gcd(int a, int b)
    {
        while (b != 0)
        {
            int c = b;
            b = a % b;
            a = c;
        }
        return a;
    }

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

    public static boolean isClassExist(String className)
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

    public static boolean isDebug(Context context)
    {
        return context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public static String filterStringNull(String str)
    {
        if (str == null || str.length() == 0) return str;
        byte[] strBytes = str.getBytes();
        ArrayList<Byte> newByte = new ArrayList<>();

        for (byte strByte : strBytes)
        {
            if (strByte != 0) newByte.add(strByte);
        }
        byte[] newByteAry = new byte[newByte.size()];
        for (int i = 0; i < newByteAry.length; i++)
            newByteAry[i] = newByte.get(i);
        return new String(newByteAry);
    }

    public static String signature2JavaType(String signature)
    {
        switch (signature)
        {
            case "V":
                return "void";
            case "Z":
                return "boolean";
            case "B":
                return "byte";
            case "C":
                return "char";
            case "S":
                return "short";
            case "I":
                return "int";
            case "J":
                return "long";
            case "F":
                return "float";
            case "D":
                return "double";
            default:
            {
                if (signature.startsWith("["))
                {
                    String tmp = signature2JavaType(signature.substring(1));
                    if (TextUtils.isEmpty(tmp))
                        return null;
                    else
                        return (tmp += "[]");
                }
                else if (signature.startsWith("L") && signature.endsWith(";"))
                {
                    return signature.substring(1, signature.length() - 1);
                }
                else
                    return null;
            }
        }
    }

    /**
     * 获取当前应用的第一个Activity的name
     *
     * @param context
     * @param pmName
     * @return
     */
    public static String getHostClzName(Context context, String pmName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pmName, PackageManager
                    .GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        ActivityInfo[] activities = packageInfo.activities;
        if (activities == null || activities.length == 0) {
            return "";
        }
        ActivityInfo activityInfo = activities[0];
        return activityInfo.name;

    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPMName(Context context) {
        // 获取当前进程已经注册的 activity
        Context applicationContext = context.getApplicationContext();
        return applicationContext.getPackageName();
    }

    @SuppressLint("PackageManagerGetSignatures")
    public static String getSignature()
    {
        Context context = BaseApplication.getAppInstance().getApplicationContext();
        try
        {
            // 获取指定包名的签名信息
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            // 获取签名数组
            Signature[] signatures = packageInfo.signatures;
            StringBuilder builder = new StringBuilder();
            for (Signature signature : signatures)
                builder.append(signature.toCharsString());
            return builder.toString();
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 设备系统版本是不是大于等于29(Android 10)
     */
    public static boolean isSdkOverIncluding29()
    {
        int SDK_INT = Build.VERSION.SDK_INT;
        return SDK_INT >= 29;
    }

    /**
     * 设备系统版本是不是大于等于28(Android 9.0 Pie)
     */
    public static boolean isSdkOverIncluding28()
    {
        int SDK_INT = Build.VERSION.SDK_INT;
        return SDK_INT >= 28;
    }

    /**
     * 设备系统版本是不是大于等于26(Android 8.0 Oreo)
     */
    public static boolean isSdkOverIncluding26()
    {
        int SDK_INT = Build.VERSION.SDK_INT;
        return SDK_INT >= 26;
    }
}
