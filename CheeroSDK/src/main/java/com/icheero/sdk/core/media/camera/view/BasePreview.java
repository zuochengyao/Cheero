package com.icheero.sdk.core.media.camera.view;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

public abstract class BasePreview
{
    private Callback mCallback;
    private int mWidth, mHeight;

    public int getWidth()
    {
        return mWidth;
    }

    public int getHeight()
    {
        return mHeight;
    }

    public void setSize(int width, int height)
    {
        this.mWidth = width;
        this.mHeight = height;
    }

    public void setCallback(Callback callback)
    {
        this.mCallback = callback;
    }

    protected void dispatchSurfaceChanged()
    {
        mCallback.onPreviewChanged();
    }

    public abstract Surface getSurface();

    public abstract View getView();

    public abstract Class getSurfaceClass();

    public abstract void setDisplayOrientation(int displayOrientation);

    public abstract boolean isReady();

    public SurfaceHolder getSurfaceHolder()
    {
        return null;
    }

    public SurfaceTexture getSurfaceTexture()
    {
        return null;
    }

    public interface Callback
    {
        void onPreviewChanged();
    }
}
