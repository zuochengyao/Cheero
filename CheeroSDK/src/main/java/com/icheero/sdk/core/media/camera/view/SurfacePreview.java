package com.icheero.sdk.core.media.camera.view;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.icheero.sdk.R;
import com.icheero.sdk.util.Log;

import androidx.core.view.ViewCompat;

public class SurfacePreview extends BasePreview
{
    private static final Class TAG = SurfacePreview.class;
    final SurfaceView mSurfaceView;

    public SurfacePreview(Context context, ViewGroup parent)
    {
        final View view = View.inflate(context, R.layout.surface_view, parent);
        mSurfaceView = view.findViewById(R.id.surface_view);
        final SurfaceHolder holder = getSurfaceHolder();
        holder.addCallback(new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                Log.i(TAG, "surfaceCreated");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
                Log.i(TAG, "surfaceChanged");
                setSize(width, height);
                if (!ViewCompat.isInLayout(mSurfaceView))
                    dispatchSurfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                Log.i(TAG, "surfaceDestroyed");
                setSize(0, 0);
            }
        });
    }

    @Override
    public Surface getSurface()
    {
        return getSurfaceHolder().getSurface();
    }

    @Override
    public View getView()
    {
        return mSurfaceView;
    }

    @Override
    public Class getSurfaceClass()
    {
        return SurfaceHolder.class;
    }

    @Override
    public void setDisplayOrientation(int displayOrientation)
    {

    }

    @Override
    public boolean isReady()
    {
        return getWidth() != 0 && getHeight() != 0;
    }

    @Override
    public SurfaceHolder getSurfaceHolder()
    {
        return mSurfaceView.getHolder();
    }
}
