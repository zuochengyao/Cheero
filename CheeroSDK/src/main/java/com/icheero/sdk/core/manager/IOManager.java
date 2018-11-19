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
    public static final String DIR_PATH_CHEERO_ROOT = DIR_PATH_BASE + "/Cheero";
    public static final String DIR_PATH_CHEERO_IMAGES = DIR_PATH_CHEERO_ROOT + "/images/";
    public static final String DIR_PATH_CHEERO_LOGS = DIR_PATH_CHEERO_ROOT + "/logs/";
    public static final String DIR_PATH_CHEERO_PATCHES = DIR_PATH_CHEERO_ROOT + "/patches/";

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
        mThreadPool.execute(() -> {
            if ((FileUtils.createDir(DIR_PATH_CHEERO_ROOT) || FileUtils.exists(DIR_PATH_CHEERO_ROOT)))
            {
                Log.i(TAG, "Create folder root: true");
                Log.i(TAG, "Create folder images: " + (FileUtils.createDir(DIR_PATH_CHEERO_IMAGES) || FileUtils.exists(DIR_PATH_CHEERO_IMAGES)));
                Log.i(TAG, "Create folder logs: " + (FileUtils.createDir(DIR_PATH_CHEERO_LOGS) || FileUtils.exists(DIR_PATH_CHEERO_LOGS)));
                Log.i(TAG, "Create folder patches: " + (FileUtils.createDir(DIR_PATH_CHEERO_PATCHES) || FileUtils.exists(DIR_PATH_CHEERO_PATCHES)));
            }
        });
    }
}
