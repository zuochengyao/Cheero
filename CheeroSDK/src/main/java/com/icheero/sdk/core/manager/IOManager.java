package com.icheero.sdk.core.manager;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 左程耀 2018年11月19日
 * IO操作管理器
 */
public class IOManager
{
    private static final Class TAG = IOManager.class;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int INIT_THREAD_COUNT = CPU_COUNT + 1;
    private static final int MAX_THREAD_COUNT = INIT_THREAD_COUNT;
    private static final long SURPLUS_THREAD_LIFE = 30L;

    private ThreadPoolExecutor mThreadPool;

    @SuppressLint("StaticFieldLeak")
    private static volatile IOManager mInstance;

    private IOManager()
    {
        mThreadPool = new ThreadPoolExecutor(INIT_THREAD_COUNT, MAX_THREAD_COUNT, SURPLUS_THREAD_LIFE, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(64), new ThreadFactory()
        {
            private AtomicInteger mInteger = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "Download Thread #" + mInteger.getAndIncrement());
            }
        });
    }

    public static IOManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (IOManager.class)
            {
                if (mInstance == null)
                    mInstance = new IOManager();
            }
        }
        return mInstance;
    }

    public void createRootFolder()
    {
        mThreadPool.execute(() -> {
            if ((FileUtils.createDir(FileUtils.DIR_PATH_CHEERO_ROOT) || FileUtils.exists(FileUtils.DIR_PATH_CHEERO_ROOT)))
            {
                Log.i(TAG, "Create folder root: true");
                Log.i(TAG, "Create folder images: " + (FileUtils.createDir(FileUtils.DIR_PATH_CHEERO_IMAGES) || FileUtils.exists(FileUtils.DIR_PATH_CHEERO_IMAGES)));
                Log.i(TAG, "Create folder logs: " + (FileUtils.createDir(FileUtils.DIR_PATH_CHEERO_LOGS) || FileUtils.exists(FileUtils.DIR_PATH_CHEERO_LOGS)));
                Log.i(TAG, "Create folder patches: " + (FileUtils.createDir(FileUtils.DIR_PATH_CHEERO_PATCHES) || FileUtils.exists(FileUtils.DIR_PATH_CHEERO_PATCHES)));
                Log.i(TAG, "Create folder cache: " + (FileUtils.createDir(FileUtils.DIR_PATH_CHEERO_CACHE) || FileUtils.exists(FileUtils.DIR_PATH_CHEERO_CACHE)));
            }
        });
    }

    /**
     * 根据url获取缓存文件，若不存在则创建
     * @param url 资源url地址
     */
    public File getCacheFileByName(String url)
    {
        return FileUtils.createFile(FileUtils.DIR_PATH_CHEERO_CACHE + Common.md5(url));
    }

    public byte[] bitmapToByte(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
