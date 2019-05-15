package com.icheero.shell;

import android.app.Application;
import android.content.Context;
import android.util.ArrayMap;

import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;
import com.icheero.sdk.util.RefInvoke;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ShellApplication extends Application
{
    private static final Class TAG = ShellApplication.class;

    private static final String APP_KEY = "APPLICATION_CLASS_NAME";
    private static final String APP_CLASS_NAME = "android.app.ActivityThread";
    private static final String APP_METHOD_NAME = "currentActivityThread";
    private static final String APP_FILED_NAME = "mPackages";

    private String apkFileName;
    private String odexPath;
    private String libPath;

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        try
        {
            File odex = getDir("payload_odex", MODE_PRIVATE);
            File libs = getDir("payload_lib", MODE_PRIVATE);
            odexPath = odex.getAbsolutePath();
            libPath = libs.getAbsolutePath();
            apkFileName = odex.getAbsolutePath() + "/payload.apk";
            File dexFile = new File(apkFileName);
            Log.i(TAG, "apk size: " + dexFile.length());
            if (!dexFile.exists())
            {
                //
                dexFile.createNewFile();
                byte[] apkData = readDexFileFromApk();
                splitPayLoadFromDex(apkData);
            }
            Object currentActivityThread = RefInvoke.invokeStaticMethod(APP_CLASS_NAME, APP_METHOD_NAME, new Class[] {}, new Object[] {});
            String packageName = getPackageName();
            ArrayMap mPackages = (ArrayMap) RefInvoke.getFieldOjbect(APP_CLASS_NAME, currentActivityThread, APP_FILED_NAME);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 释放被加壳的apk文件，so文件
     * @param apkData
     * @throws IOException
     */
    private void splitPayLoadFromDex(byte[] apkData) throws IOException
    {
        int apkLen = apkData.length;
        byte[] dexLen = new byte[4];
        FileUtils.copyBytes(apkData, apkLen - 4, dexLen);
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(dexLen));
        int read = inputStream.readInt();
        Log.i(TAG, "Data:" + Integer.toHexString(read));
        byte[] newDex = new byte[read];
        FileUtils.copyBytes(apkData, apkLen - 4 - read, newDex);
        newDex = decrypt(newDex);

    }

    private byte[] readDexFileFromApk() throws IOException
    {
        ByteArrayOutputStream dexOutputStream = new ByteArrayOutputStream();
        ZipInputStream localZipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(getApplicationInfo().sourceDir)));
        while (true)
        {
            ZipEntry localZipEntry = localZipInputStream.getNextEntry();
            if (localZipEntry == null) break;
            if (localZipEntry.getName().equals("classes.dex"))
            {
                byte[] data = new byte[1024];
                int i = 0;
                while ((i = localZipInputStream.read(data)) != -1) dexOutputStream.write(data, 0, i);
                localZipInputStream.closeEntry();
            }
        }
        localZipInputStream.close();
        return dexOutputStream.toByteArray();
    }

    private byte[] decrypt(byte[] srcdata)
    {
        for (int i = 0; i < srcdata.length; i++)
        {
            srcdata[i] = (byte) (0xFF ^ srcdata[i]);
        }
        return srcdata;
    }

}
