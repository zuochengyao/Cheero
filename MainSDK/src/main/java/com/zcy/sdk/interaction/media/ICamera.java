package com.zcy.sdk.interaction.media;

import android.content.Context;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;

/**
 * Created by zuochengyao on 2018/2/5.
 */

public class ICamera
{
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

}
