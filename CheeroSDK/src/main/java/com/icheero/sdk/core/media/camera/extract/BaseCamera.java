package com.icheero.sdk.core.media.camera.extract;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class BaseCamera
{
    public static final int FACING_BACK = 0;
    public static final int FACING_FRONT = 1;

    public static final int FLASH_OFF = 0;
    public static final int FLASH_ON = 1;
    public static final int FLASH_TORCH = 2;
    public static final int FLASH_AUTO = 3;
    public static final int FLASH_RED_EYE = 4;

    public static final int LANDSCAPE_90 = 90;
    public static final int LANDSCAPE_270 = 270;

    protected Callback mCallback;
    // protected BasePreview mPreview;

//    public BaseCamera(Callback callback, BasePreview preview)
//    {
//        this.mCallback = callback;
//        this.mPreview = preview;
//    }

    protected Activity mActivity;
    protected int mCameraId;
    protected View mPreview;

    public BaseCamera(Activity activity, @NonNull View preview, int cameraId)
    {
        this.mActivity = activity;
        this.mCameraId = cameraId;
        this.mPreview = preview;
    }

    protected abstract boolean open();

    protected abstract void close();

    protected abstract boolean isCameraOpened();

    protected abstract void setFacing(int facing);

    protected abstract int getFacing();

    protected abstract void setAutoFocus(boolean autoFocus);

    protected abstract boolean getAutoFocus();

    protected abstract void setFlash(int flash);

    protected abstract int getFlash();

    protected abstract void takePicture();

    protected abstract void setDisplayOrientation(int orientation);

    public interface Callback
    {
        void onOpened(int width, int height);

        void onClosed();

        void onPictureTaken(byte[] data);
    }
}
