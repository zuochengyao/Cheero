package com.icheero.sdk.core.storage.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.icheero.sdk.core.storage.file.dir.DirectoryBase;
import com.icheero.sdk.core.storage.file.dir.DirectoryFactory;
import com.icheero.sdk.core.storage.file.dir.external.ExternalDirectoryFactory;
import com.icheero.sdk.core.storage.file.dir.internal.InternalDirectoryFactory;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * @author 左程耀 2018年11月19日
 * IO操作管理器
 */
public class FileScopeManager
{
    private static final Class<FileScopeManager> TAG = FileScopeManager.class;

    private Context mBaseContext;
    @SuppressLint("StaticFieldLeak")
    private static volatile FileScopeManager mInstance;

    // region 内部存储 /data/data/package
    public static final String DIR_CACHE = "cache";
    public static final String DIR_FILES = "files";
    // endregion

    // region 内部存储 /data/data/package/files
    public static final String DIR_INTERNAL_FILES_PATCH = "patch";
    // endregion

    // region 外部私有存储 /storage/emulate/0/Android/data/package/files
    public static final String DIR_EXTERNAL_FILES_IMAGE = "Image";
    public static final String DIR_EXTERNAL_FILES_VIDEO = "Video";
    public static final String DIR_EXTERNAL_FILES_AUDIO = "Audio";
    public static final String DIR_EXTERNAL_FILES_PLUGIN = "Plugin";
    public static final String DIR_EXTERNAL_FILES_LOG = "Log";
    // endregion

    private DirectoryFactory mDirectoryFactory;

    private FileScopeManager()
    { }

    public static FileScopeManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (FileScopeManager.class)
            {
                if (mInstance == null)
                    mInstance = new FileScopeManager();
            }
        }
        return mInstance;
    }

    public synchronized void init(Context context)
    {
        if (mBaseContext == null)
            this.mBaseContext = context.getApplicationContext();
        new Thread(this::initAppFolders).start();
    }

    private void initAppFolders()
    {
        internal(DIR_FILES).init(mBaseContext);
        internal(DIR_CACHE).init(mBaseContext);
        external(DIR_FILES).init(mBaseContext);
        external(DIR_CACHE).init(mBaseContext);
    }

    public DirectoryBase external(String rootDir)
    {
        if (TextUtils.isEmpty(rootDir))
            throw new NullPointerException("Param parentDir is NULL");
        mDirectoryFactory = new ExternalDirectoryFactory();
        return mDirectoryFactory.get(rootDir);
    }

    public DirectoryBase internal(String rootDir)
    {
        if (TextUtils.isEmpty(rootDir))
            throw new NullPointerException("Param parentDir is NULL");
        mDirectoryFactory = new InternalDirectoryFactory();
        return mDirectoryFactory.get(rootDir);
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
