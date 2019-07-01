package com.icheero.sdk.core.media.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.view.SurfaceHolder;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2
{
    private static final Class TAG = Camera2.class;

    private CameraManager mCameraManager;
    private CameraCaptureSession mCameraSession;
    private CameraDevice mDevice;
    private SurfaceHolder mSurfaceHolder;

    public Camera2(Context context)
    {
        mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }
}
