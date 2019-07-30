package com.icheero.sdk.core.manager;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

import com.icheero.sdk.core.media.camera.Camera1;
import com.icheero.sdk.core.media.camera.Camera2;
import com.icheero.sdk.core.media.camera.extract.BaseCamera;

import java.io.File;

public class CameraManager
{
    public static final int REQUEST_CODE_IMAGE = 100;
    public static final int REQUEST_CODE_VIDEO = 101;
    private BaseCamera mCamera;

    public CameraManager(Activity activity, View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mCamera = new Camera2(activity, view);
        else
            mCamera = new Camera1(activity, view);
    }

    public static void openSystemImageCamera(Activity activity) throws ActivityNotFoundException
    {
        if (activity != null)
            openSystemImageCamera(activity, null);
    }

    public static void openSystemImageCamera(Activity activity, File file) throws ActivityNotFoundException
    {
        if (activity != null)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (file != null) intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            activity.startActivityForResult(intent, REQUEST_CODE_IMAGE);
        }
    }

    public static void openSystemVideoCamera(Activity activity, File file) throws ActivityNotFoundException
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
