package com.icheero.sdk.core.media.camera.extract;

import android.app.Activity;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class BaseCamera
{
    protected Class TAG;

    protected static final int FACING_BACK = 0;
    protected static final int FACING_FRONT = 1;

    protected static final int FLASH_OFF = 0;
    protected static final int FLASH_ON = 1;
    protected static final int FLASH_TORCH = 2;
    protected static final int FLASH_AUTO = 3;
    protected static final int FLASH_RED_EYE = 4;

    protected static final int LANDSCAPE_90 = 90;
    protected static final int LANDSCAPE_270 = 270;

    protected final SizeMap mPreviewSizes = new SizeMap();
    protected final SizeMap mPictureSizes = new SizeMap();
    protected Callback mCallback;
    protected AspectRatio mAspectRatio;
    protected boolean isAutoFocus = false;
    protected int mFlash;
    protected int mDisplayOrientation = 0;
    protected Activity mActivity;
    protected int mCameraId;
    protected View mPreview;
    protected SurfaceView mSurfaceView;
    protected TextureView mTextureView;

    // protected BasePreview mPreview;

    //    public BaseCamera(Callback callback, BasePreview preview)
    //    {
    //        this.mCallback = callback;
    //        this.mPreview = preview;
    //    }

    public BaseCamera(Activity activity, @NonNull View preview, int cameraId)
    {
        TAG = getClass();
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
        /**
         * 返回最佳预览比例的宽高（Preview不会变形）
         */
        void onOpened(int width, int height);

        void onClosed();

        void onPictureTaken(byte[] data);
    }
}
