package com.zcy.sdk.interaction.media;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.File;

/**
 * Created by zuochengyao on 2018/2/5.
 */

public class ICamera
{
    public static final int REQUEST_CODE_IMAGE = 100;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static
    {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private static volatile ICamera mInstance;

    private Context mContext;
    private CameraManager mCameraManager;
    private CameraCaptureSession mCameraSession;
    private CameraDevice mDevice;
    private SurfaceHolder mSurfaceHolder;

    private ICamera(Context context)
    {
        // 防止内存泄露
        mContext = context.getApplicationContext();
        mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    public static ICamera getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (ICamera.class)
            {
                if (mInstance == null)
                    mInstance = new ICamera(context);
            }
        }
        return mInstance;
    }

    public void openSystemCamera(Activity activity) throws ActivityNotFoundException
    {
        if (activity != null)
            openSystemCamera(activity, null);
    }

    public void openSystemCamera(Activity activity, File file) throws ActivityNotFoundException
    {
        if (activity != null)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (file != null)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            activity.startActivityForResult(intent, REQUEST_CODE_IMAGE);
        }
    }

}
