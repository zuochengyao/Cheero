package com.icheero.shell;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.ArrayMap;

import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;
import com.icheero.sdk.util.RefUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import dalvik.system.DexClassLoader;

public class ShellApplication extends Application
{
    private static final Class<?> TAG = ShellApplication.class;

    private static final String APP_KEY = "APPLICATION_CLASS_NAME";

    private static final String APP_FILED_NAME_PACKAGES = "mPackages";
    private static final String APP_FILED_NAME_APPLICATION = "mApplication";
    private static final String APP_FILED_NAME_CLASSLOADER = "mClassLoader";
    private static final String APP_FILED_NAME_BOUND_APPLICATION = "mBoundApplication";
    private static final String APP_FILED_NAME_INFO = "info";
    private static final String APP_FILED_NAME_APP_INFO = "appInfo";
    private static final String APP_FILED_NAME_INITIAL_APPLICATION = "mInitialApplication";
    private static final String APP_FILED_NAME_ALL_APPLICATION = "mAllApplications";
    private static final String APP_FILED_NAME_MAKE_APPLICATION = "makeApplication";
    private static final String APP_FILED_NAME_APPLICATION_INFO = "mApplicationInfo";
    private static final String APP_FILED_NAME_PROVIDER_MAP = "mProviderMap";
    private static final String APP_FILED_NAME_LOCAL_PROVIDER = "mLocalProvider";
    private static final String APP_FILED_NAME_CONTEXT = "mContext";

    private String apkFileName;
    private String odexPath;
    private String libPath;

    /**
     * 得到脱壳Apk中的dex文件，然后从这个文件中得到源程序Apk.进行解密，然后加载
     * @param base
     */
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
                dexFile.createNewFile();
                byte[] apkData = readDexFileFromApk();
                splitPayLoadFromDex(apkData);
            }
            Object currentActivityThread = RefUtils.invokeStaticMethod(RefUtils.CLASS_ACTIVITY_THREAD, RefUtils.METHOD_CURRENT_ACTIVITY_THREAD, new Class[] { }, new Object[] { });
            String packageName = getPackageName();
            ArrayMap<?, ?> mPackages = (ArrayMap<?, ?>) RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD,
                                                                                             APP_FILED_NAME_PACKAGES, currentActivityThread);
            WeakReference<?> weakRef = (WeakReference<?>) mPackages.get(packageName);
            DexClassLoader dexLoader = new DexClassLoader(apkFileName, odexPath, libPath,
                                                          (ClassLoader) RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_LOADED_APK, APP_FILED_NAME_CLASSLOADER, weakRef.get()));
            RefUtils.setFieldObject(RefUtils.CLASS_LOADED_APK, APP_FILED_NAME_CLASSLOADER, weakRef.get(), dexLoader);
            Log.i(TAG, "Classloader: " + dexLoader);
            Object actObj = dexLoader.loadClass(RefUtils.CLASS_MAIN_ACTIVITY);
            if (actObj != null)
                Log.i(TAG, "actObj: " + actObj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.i(TAG, "onCreate");
        String appClassName = null;
        try
        {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = appInfo.metaData;
            if (bundle != null && bundle.containsKey(APP_KEY))
                appClassName = bundle.getString(APP_KEY);
            else
            {
                Log.i(TAG, "There is no classname in application!");
                return;
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Log.i(TAG, "error: " + e.getMessage());
        }
        try
        {
            Object currentActivityThread = RefUtils.invokeStaticMethod(RefUtils.CLASS_ACTIVITY_THREAD, RefUtils.METHOD_CURRENT_ACTIVITY_THREAD, new Class[] { }, new Object[] { });
            Object mBoundApplication = RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD, APP_FILED_NAME_BOUND_APPLICATION, currentActivityThread);
            Object loadedApkInfo = RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD_APP_BIND_DATA, APP_FILED_NAME_INFO, mBoundApplication);

            RefUtils.setFieldObject(RefUtils.CLASS_LOADED_APK, APP_FILED_NAME_APPLICATION, loadedApkInfo, null);
            Object oldApplication = RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD, APP_FILED_NAME_INITIAL_APPLICATION, currentActivityThread);
            List<Application> allApplications = (ArrayList<Application>) RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD, APP_FILED_NAME_ALL_APPLICATION);
            allApplications.remove(oldApplication);

            ApplicationInfo loadedApkApplicationInfo = (ApplicationInfo) RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD, APP_FILED_NAME_APPLICATION_INFO, loadedApkInfo);
            ApplicationInfo bindDataApplicationInfo = (ApplicationInfo) RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD_APP_BIND_DATA, APP_FILED_NAME_APP_INFO, mBoundApplication);
            loadedApkApplicationInfo.className = appClassName;
            bindDataApplicationInfo.className = appClassName;
            Application app = (Application) RefUtils.invokeMethod(RefUtils.CLASS_LOADED_APK, APP_FILED_NAME_MAKE_APPLICATION, loadedApkInfo, new Class[] {boolean.class, Instrumentation.class}, new Object[] {false, null});
            RefUtils.setFieldObject(RefUtils.CLASS_ACTIVITY_THREAD, APP_FILED_NAME_INITIAL_APPLICATION, currentActivityThread, app);

            ArrayMap mProviderMap = (ArrayMap) RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD, APP_FILED_NAME_PROVIDER_MAP, currentActivityThread);
            Iterator it = mProviderMap.values().iterator();
            while (it.hasNext())
            {
                Object providerClientRecord = it.next();
                Object localProvider = RefUtils.getObjectDeclaredFieldValue(RefUtils.CLASS_ACTIVITY_THREAD_PROVIDER_CLIENT_RECORD, APP_FILED_NAME_LOCAL_PROVIDER, providerClientRecord);
                RefUtils.setFieldObject(RefUtils.CLASS_CONTENT_PROVIDER, APP_FILED_NAME_CONTEXT, localProvider, app);
            }
            Log.i(TAG, "app: " + app);
            app.onCreate();
        }
        catch (Exception e)
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

        File file = new File(apkFileName);
        FileOutputStream localOutStream = new FileOutputStream(file);
        localOutStream.write(newDex);
        localOutStream.close();

        ZipInputStream localZipInStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
        while (true)
        {
            ZipEntry localZipEntry = localZipInStream.getNextEntry();
            if (localZipEntry == null)
            {
                localZipInStream.close();
                break;
            }
            //取出被加壳apk用到的so文件，放到 libPath中（data/data/包名/payload_lib)
            String name = localZipEntry.getName();
            if (name.startsWith("lib/") && name.endsWith(".so"))
            {
                File storeFile = new File(libPath + "/" + name.substring(name.lastIndexOf('/')));
                if (storeFile.createNewFile())
                {
                    FileOutputStream fos = new FileOutputStream(storeFile);
                    byte[] buffer = new byte[1024];
                    int i;
                    while ((i = localZipInStream.read(buffer)) != -1)
                    {
                        fos.write(buffer, 0, i);
                    }
                    fos.flush();
                    fos.close();
                }
                localZipInStream.closeEntry();
            }
            localZipInStream.close();
        }
    }

    private byte[] readDexFileFromApk() throws IOException
    {
        ByteArrayOutputStream dexOutputStream = new ByteArrayOutputStream();
        ZipInputStream localZipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(getApplicationInfo().sourceDir)));
        while (true)
        {
            ZipEntry localZipEntry = localZipInputStream.getNextEntry();
            if (localZipEntry == null)
                break;
            if (localZipEntry.getName().equals("classes.dex"))
            {
                byte[] data = new byte[1024];
                int i = 0;
                while ((i = localZipInputStream.read(data)) != -1)
                    dexOutputStream.write(data, 0, i);
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
