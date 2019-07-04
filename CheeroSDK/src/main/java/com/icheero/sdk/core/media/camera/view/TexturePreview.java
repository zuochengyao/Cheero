package com.icheero.sdk.core.media.camera.view;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.View;

public class TexturePreview extends BasePreview
{
    @Override
    public Surface getSurface()
    {
        return null;
    }

    @Override
    public View getView()
    {
        return null;
    }

    @Override
    public Class getSurfaceClass()
    {
        return null;
    }

    @Override
    public void setDisplayOrientation(int displayOrientation)
    {

    }

    @Override
    public boolean isReady()
    {
        return false;
    }

    @Override
    public SurfaceTexture getSurfaceTexture()
    {
        return super.getSurfaceTexture();
    }
}
