package com.icheero.sdk.core.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.IOUtils;
import com.icheero.sdk.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * @author 左程耀 2018年11月19日
 * IO操作管理器
 */
public class FileManager
{
    private static final Class<FileManager> TAG = FileManager.class;

    private Context mBaseContext;
    @SuppressLint("StaticFieldLeak")
    private static volatile FileManager mInstance;

    // region 内部存储 /data/data/package/files
    public static final String DIR_FILES_APATCH = "/apatch";
    public static final String DIR_FILES_PLUGIN = "/plugin";
    // endregion

    // region 外部私有存储 /storage/emulate/0/Android/data/package/files
    public static final String DIR_EXTERNAL_FILES_IMAGE = "/Image";
    public static final String DIR_EXTERNAL_FILES_VIDEO = "/Video";
    public static final String DIR_EXTERNAL_FILES_AUDIO = "/Audio";
    public static final String DIR_EXTERNAL_FILES_LOG = "/Log";
    // endregion

    private FileManager()
    { }

    public static FileManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (FileManager.class)
            {
                if (mInstance == null)
                    mInstance = new FileManager();
            }
        }
        return mInstance;
    }

    public synchronized void init(Context context)
    {
        if (mBaseContext == null)
            this.mBaseContext = context.getApplicationContext();
        new Thread(this::createAppFolders).start();
    }

    private void createAppFolders()
    {
        Log.i(TAG, "Create folder plugin: " + IOUtils.createDir(mBaseContext.getFilesDir(), DIR_FILES_PLUGIN));
        Log.i(TAG, "Create folder image: " + IOUtils.createDir(
                mBaseContext.getExternalFilesDir(DIR_EXTERNAL_FILES_IMAGE).getAbsolutePath()));
        Log.i(TAG, "Create folder download: " + IOUtils.createDir(
                mBaseContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()));
        Log.i(TAG, "Create folder video: " + IOUtils.createDir(
                mBaseContext.getExternalFilesDir(DIR_EXTERNAL_FILES_VIDEO).getAbsolutePath()));
        Log.i(TAG, "Create folder audio: " + IOUtils.createDir(
                mBaseContext.getExternalFilesDir(DIR_EXTERNAL_FILES_AUDIO).getAbsolutePath()));
        Log.i(TAG, "Create folder log: " + IOUtils.createDir(
                mBaseContext.getExternalFilesDir(DIR_EXTERNAL_FILES_LOG).getAbsolutePath()));
    }

    public File createDownloadFile(String filename)
    {
        return IOUtils.createFile(mBaseContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
    }

    public boolean downloadFileExist(String filename)
    {
        return IOUtils.exists(mBaseContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
    }

    public File createImageFile(String filename)
    {
        return IOUtils.createFile(mBaseContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
    }

    /**
     * 根据url获取缓存文件，若不存在则创建
     * @param url 资源url地址
     */
    public File getCacheFileByName(String url)
    {
        //IOUtils.DIR_PATH_CHEERO_CACHE +
        return IOUtils.createFile(Common.md5(url));
    }

    public byte[] bitmapToByte(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public String saveImageFile(byte[] data)
    {
        return IOUtils.saveFile(System.currentTimeMillis() + ".jpg", data);
    }
}
