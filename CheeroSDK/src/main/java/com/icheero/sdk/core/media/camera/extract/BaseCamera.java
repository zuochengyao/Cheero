package com.icheero.sdk.core.media.camera.extract;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class BaseCamera
{
    protected Class TAG;

    protected static final int FACING_BACK = 0;
    protected static final int FACING_FRONT = 1;
    protected static final int FACING_EXTERNAL = 2;

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
    protected int mCameraId;
    protected boolean isAutoFocus = false;
    protected int mFlash;
    protected int mDisplayOrientation = 0;
    protected Activity mActivity;
    protected View mPreview;
    protected SurfaceView mSurfaceView;
    protected TextureView mTextureView;
    protected int mMaxWidth = 1920, mMaxHeight = 1080;
    protected AspectRatio mAspectRatio = AspectRatio.DEFAULT;

    // protected BasePreview mPreview;

    //    public BaseCamera(Callback callback, BasePreview preview)
    //    {
    //        this.mCallback = callback;
    //        this.mPreview = preview;
    //    }

    public BaseCamera(Activity activity, @NonNull View preview)
    {
        TAG = getClass();
        this.mActivity = activity;

        this.mPreview = preview;
        if (preview instanceof SurfaceView)
            mSurfaceView = (SurfaceView) preview;
        else if (preview instanceof TextureView)
            mTextureView = (TextureView) preview;
        else
            throw new IllegalArgumentException("Preview must be SurfaceView or TextureView");
    }

    public void setCallback(Callback callback)
    {
        this.mCallback = callback;
    }

    public void setMaxSize(int width, int height)
    {
        this.mMaxWidth = width;
        this.mMaxHeight = height;
        mAspectRatio = AspectRatio.of(width, height).inverse();
    }

    protected Class getOutputClass()
    {
        if (mPreview instanceof SurfaceView)
            return SurfaceHolder.class;
        else
            return SurfaceTexture.class;
    }

    protected Surface getSurface()
    {
        if (mPreview instanceof SurfaceView)
            return mSurfaceView.getHolder().getSurface();
        else if (mPreview instanceof TextureView)
            return new Surface(mTextureView.getSurfaceTexture());
        else
            return null;
    }

    public abstract boolean open();

    public abstract void close();

    public abstract boolean isCameraOpened();

    public abstract void setCameraId(int facing);

    public abstract int getCameraId();

    public abstract void setAutoFocus(boolean autoFocus);

    public abstract boolean getAutoFocus();

    public abstract void setFlash(int flash);

    public abstract int getFlash();

    public abstract void takePicture();

    public abstract void setDisplayOrientation(int orientation);

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
