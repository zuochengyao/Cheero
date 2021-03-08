package com.icheero.sdk.core.storage.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.Executors;

/**
 * @author 左程耀 2018年11月19日
 * IO操作管理器
 */
public class FileScopeManager
{
    private static final Class<FileScopeManager> TAG = FileScopeManager.class;

    // region
    public static final String DIR_CACHE = "cache";
    public static final String DIR_FILES = "files";
    // endregion

    // region 内部存储 /data/data/package/files
    public static final String DIR_INTERNAL_FILES_PATCH = "patch";
    public static final String DIR_INTERNAL_FILES_PLUGIN = "plugin";
    // endregion

    // region 外部私有存储 /storage/emulate/0/Android/data/package/files
    public static final String DIR_EXTERNAL_FILES_DATA = "Data";
    public static final String DIR_EXTERNAL_FILES_LOG = "Log";
    // endregion

    private Context mAppContext;
    private static volatile FileScopeManager mInstance;

    private FileScopeManager() { }

    public static FileScopeManager getInstance() {
        if (mInstance == null)
        {
            synchronized (FileScopeManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new FileScopeManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context)
    {
        mAppContext = context.getApplicationContext();
        Executors.newSingleThreadExecutor().execute(() -> {
            IOUtils.createDir(mAppContext.getFilesDir(), FileScopeManager.DIR_INTERNAL_FILES_PATCH);
            IOUtils.createDir(mAppContext.getFilesDir(),
                    FileScopeManager.DIR_INTERNAL_FILES_PLUGIN);
            mAppContext.getExternalFilesDir(
                    FileScopeManager.DIR_EXTERNAL_FILES_DATA + "/" + FileScopeManager.DIR_EXTERNAL_FILES_LOG);
        });
    }

    public File getPluginFile(String filename)
    {
        File pluginDir = new File(mAppContext.getFilesDir(),
                FileScopeManager.DIR_INTERNAL_FILES_PLUGIN);
        return new File(pluginDir, filename);
    }

    public File createDownloadFile(String filename)
    {
        return IOUtils.createFile(mAppContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                filename);
    }

    public boolean downloadFileExist(String filename)
    {
        return IOUtils.exists(mAppContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                filename);
    }

    public File createImageFile(String filename)
    {
        return IOUtils.createFile(mAppContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                filename);
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
