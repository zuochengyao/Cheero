package com.icheero.sdk.core.manager;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class CameraManager
{
    public static final int REQUEST_CODE_IMAGE = 100;
    public static final int REQUEST_CODE_VIDEO = 101;
    private static volatile CameraManager mInstance;

    private CameraManager()
    { }

    public static CameraManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (CameraManager.class)
            {
                if (mInstance == null)
                    mInstance = new CameraManager();
            }
        }
        return mInstance;
    }

    public void openSystemImageCamera(Activity activity) throws ActivityNotFoundException
    {
        if (activity != null) openSystemImageCamera(activity, null);
    }

    public void openSystemImageCamera(Activity activity, File file) throws ActivityNotFoundException
    {
        if (activity != null)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (file != null) intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            activity.startActivityForResult(intent, REQUEST_CODE_IMAGE);
        }
    }

    public void openSystemVideoCamera(Activity activity, File file) throws ActivityNotFoundException
    {
        if (activity != null)
        {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            // 添加（可选）附加信息以将视频保存到指定文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            activity.startActivityForResult(intent, REQUEST_CODE_VIDEO);
        }
    }
}
