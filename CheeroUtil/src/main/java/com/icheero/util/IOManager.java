package com.icheero.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    public static final String DIR_PATH_BASE = Environment.getExternalStorageDirectory().getPath();
    public static final String DIR_PATH_CHEERO_ROOT = DIR_PATH_BASE + "/Cheero";
    public static final String DIR_PATH_CHEERO_IMAGES = DIR_PATH_CHEERO_ROOT + "/images/";
    public static final String DIR_PATH_CHEERO_LOGS = DIR_PATH_CHEERO_ROOT + "/logs/";
    public static final String DIR_PATH_CHEERO_PATCHES = DIR_PATH_CHEERO_ROOT + "/patches/";
    public static final String DIR_PATH_CHEERO_CACHE = DIR_PATH_CHEERO_ROOT + "/cache/";

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int INIT_THREAD_COUNT = CPU_COUNT + 1;
    private static final int MAX_THREAD_COUNT = INIT_THREAD_COUNT;
    private static final long SURPLUS_THREAD_LIFE = 30L;

    private Context mContext;
    private ThreadPoolExecutor mThreadPool;

    @SuppressLint("StaticFieldLeak")
    private static volatile IOManager mInstance;

    private IOManager()
    {

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

    public void init(Context context)
    {
        this.mContext = context;
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

    public void createRootFolder()
    {
        mThreadPool.execute(() -> {
            if ((FileUtils.createDir(IOManager.DIR_PATH_CHEERO_ROOT) || FileUtils.exists(IOManager.DIR_PATH_CHEERO_ROOT)))
            {
                Log.i(TAG, "Create folder root: true");
                Log.i(TAG, "Create folder images: " + (FileUtils.createDir(IOManager.DIR_PATH_CHEERO_IMAGES) || FileUtils.exists(IOManager.DIR_PATH_CHEERO_IMAGES)));
                Log.i(TAG, "Create folder logs: " + (FileUtils.createDir(IOManager.DIR_PATH_CHEERO_LOGS) || FileUtils.exists(IOManager.DIR_PATH_CHEERO_LOGS)));
                Log.i(TAG, "Create folder patches: " + (FileUtils.createDir(IOManager.DIR_PATH_CHEERO_PATCHES) || FileUtils.exists(IOManager.DIR_PATH_CHEERO_PATCHES)));
                Log.i(TAG, "Create folder cache: " + (FileUtils.createDir(IOManager.DIR_PATH_CHEERO_CACHE) || FileUtils.exists(IOManager.DIR_PATH_CHEERO_CACHE)));
            }
        });
    }

    /**
     * 根据url获取缓存文件，若不存在则创建
     * @param url 资源url地址
     */
    public File getCacheFileByName(String url)
    {
        return FileUtils.createFile(IOManager.DIR_PATH_CHEERO_CACHE + Common.md5(url));
    }

    public byte[] bitmapToByte(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 获取响应body数据
     */
    public byte[] getResponseData(long contentLength, InputStream body)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) contentLength);
        int length;
        byte[] data = new byte[1024];
        try
        {
            while ((length = body.read(data)) != -1)
                outputStream.write(data, 0, length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
