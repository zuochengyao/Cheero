package com.icheero.sdk.core.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.alibaba.android.arouter.thread.DefaultPoolExecutor;
import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import java.util.concurrent.ThreadPoolExecutor;

public class IOManager
{
    private static final Class TAG = IOManager.class;

    public static final String DIR_PATH_BASE = Environment.getExternalStorageDirectory().getPath();
    public static final String DIR_PATH_DATA = Environment.getDataDirectory().getPath();
    public static final String DIR_PATH_CACHE = Environment.getDownloadCacheDirectory().getPath();
    public static final String DIR_PATH_CHEERO_ROOT = DIR_PATH_BASE + "/Cheero";
    public static final String DIR_PATH_CHEERO_IMAGES = DIR_PATH_CHEERO_ROOT + "/images";
    public static final String DIR_PATH_CHEERO_LOGS = DIR_PATH_CHEERO_ROOT + "/logs";

    private Context mContext;
    private ThreadPoolExecutor mThreadPool;

    @SuppressLint("StaticFieldLeak")
    private static volatile IOManager mInstance;

    private IOManager()
    {
        this.mContext = BaseApplication.getAppInstance();
        mThreadPool = DefaultPoolExecutor.getInstance();
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
        mThreadPool.execute(new Runnable() {
            @Override
            public void run()
            {
                Log.i(TAG, "Create root folder " + (FileUtils.createDir(DIR_PATH_CHEERO_ROOT) || FileUtils.exists(DIR_PATH_CHEERO_ROOT)));
            }
        });
    }
}
