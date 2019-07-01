package com.icheero.sdk.core.media.camera;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.icheero.sdk.core.media.camera.extract.ICamera;
import com.icheero.sdk.util.Log;

import java.io.File;

import androidx.collection.SparseArrayCompat;

/**
 * Created by zuochengyao on 2018/2/5.
 */

public class Camera1 implements ICamera
{
    private static final Class TAG = Camera1.class;

    public static final int REQUEST_CODE_IMAGE = 100;
    public static final int REQUEST_CODE_VIDEO = 101;

    private static final SparseIntArray CAMERA_ORIENTATIONS = new SparseIntArray();
    private static final SparseArrayCompat<String> CAMERA_FLASH_MODES = new SparseArrayCompat<>();

    static
    {
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_0, 90);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_90, 0);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_180, 270);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_270, 180);

        CAMERA_FLASH_MODES.put(ICamera.FLASH_OFF, Camera.Parameters.FLASH_MODE_OFF);
        CAMERA_FLASH_MODES.put(ICamera.FLASH_ON, Camera.Parameters.FLASH_MODE_ON);
        CAMERA_FLASH_MODES.put(ICamera.FLASH_TORCH, Camera.Parameters.FLASH_MODE_TORCH);
        CAMERA_FLASH_MODES.put(ICamera.FLASH_AUTO, Camera.Parameters.FLASH_MODE_AUTO);
        CAMERA_FLASH_MODES.put(ICamera.FLASH_RED_EYE, Camera.Parameters.FLASH_MODE_RED_EYE);
    }

    private Context mContext;
    private int mCameraId;
    private Camera mCamera;
    private Camera.CameraInfo mCameraInfo;
    private Camera.Parameters mParameters;
    private SurfaceHolder mSurfaceHolder;

    public Camera1(Context context)
    {
        this(context, Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public Camera1(Context context, int cameraId)
    {
        this.mContext = context;
        this.mCameraId = cameraId;
        this.mCameraInfo = new Camera.CameraInfo();
    }

    @Override
    public boolean open()
    {
        if (checkCameraId())
        {
            openCamera();
            if (mCamera != null)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void close()
    {
        releaseCamera();
    }

    @Override
    public boolean isCameraOpened()
    {
        return false;
    }

    @Override
    public void setFacing(int facing)
    {

    }

    @Override
    public int getFacing()
    {
        return 0;
    }

    @Override
    public void setAutoFocus(boolean autoFocus)
    {

    }

    @Override
    public boolean getAutoFocus()
    {
        return false;
    }

    @Override
    public void setFlash(int flash)
    {

    }

    @Override
    public int getFlash()
    {
        return 0;
    }

    @Override
    public void takePicture()
    {

    }

    @Override
    public void setDisplayOrientation(int orientation)
    {
        mCamera.setDisplayOrientation(orientation);
    }

    public void setSurfaceHolder(SurfaceHolder holder)
    {
        this.mSurfaceHolder = holder;
    }

    private boolean checkCameraId()
    {
        for (int i = 0, count = Camera.getNumberOfCameras(); i < count; i++)
        {
            if (mCameraId == i)
                return true;
        }
        return false;
    }

    private void openCamera()
    {
        if (mCamera != null)
            releaseCamera();
        try
        {
            mCamera = Camera.open(mCameraId);
            mParameters = mCamera.getParameters();
            mParameters.getSupportedPreviewSizes();
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        }
        catch (Exception e)
        {
            Log.e(TAG, "Open camera failed.CameraId = " + mCameraId);
        }
    }

    private void releaseCamera()
    {
        if (mCamera != null)
        {
            mCamera.release();
            mCamera = null;
        }
    }

    public void openSystemImageCamera(Activity activity) throws ActivityNotFoundException
    {
        if (activity != null)
            openSystemImageCamera(activity, null);
    }

    public void openSystemImageCamera(Activity activity, File file) throws ActivityNotFoundException
    {
        if (activity != null)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (file != null)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
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
