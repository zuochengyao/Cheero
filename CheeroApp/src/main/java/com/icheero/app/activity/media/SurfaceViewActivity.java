package com.icheero.app.activity.media;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.media.camera.Camera1;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurfaceViewActivity extends BaseActivity implements SurfaceHolder.Callback
{
    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;

    private Camera1 mCamera;
    private SurfaceHolder mSurfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        mCamera = new Camera1(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.i(TAG, "surfaceCreated");
        // mCamera.setDisplayOrientation(90);
        mCamera.setSurfaceHolder(mSurfaceHolder);
        mCamera.open();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log.i(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.i(TAG, "surfaceDestroyed");
        mCamera.close();
    }
}
