package com.icheero.app.activity.media;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.media.camera.Camera1;
import com.icheero.sdk.core.media.camera.extract.BaseCamera;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurfaceViewActivity extends BaseActivity implements SurfaceHolder.Callback, BaseCamera.Callback
{
    @BindView(R.id.surface_root)
    RelativeLayout root;
    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;

    private Camera1 mCamera;
    private SurfaceHolder mSurfaceHolder;
    private int mScreenHeight, mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        doInitView();
        Log.i(TAG, "StatusBarHeight:" + getStatusBarHeight() +", ScreenWidth:" + mScreenWidth + ", ScreenHeight:" + mScreenHeight);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mCamera != null)
            mCamera.close();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        // mSurfacePreview = new SurfacePreview(this, root);
        // SurfaceView surfaceView = (SurfaceView) mSurfacePreview.getView();
        mCamera = new Camera1(this, mSurfaceView);
        mCamera.setCallback(this);
        DisplayMetrics dMetrics = getResources().getDisplayMetrics();
        mScreenHeight = dMetrics.heightPixels;
        mScreenWidth = dMetrics.widthPixels;
        mCamera.setMaxSize(mScreenWidth, mScreenHeight - getStatusBarHeight());
        mSurfaceView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.i(TAG, "surfaceCreated");
        // 设置预览角度
        if (mCamera != null)
            mCamera.open();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log.i(TAG, "surfaceChanged：" + width + " * " + height + "");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.i(TAG, "surfaceDestroyed");
        if (mCamera != null)
            mCamera.close();
    }

    @Override
    public void onOpened(int width, int height)
    {
        Log.i(TAG, "onOpened：" + width + " * " + height + "");
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSurfaceView.getLayoutParams();
        params.width = height;
        params.height = width;
        mSurfaceView.post(() -> mSurfaceView.setLayoutParams(params));
    }

    @Override
    public void onClosed()
    {

    }

    @Override
    public void onPictureTaken(byte[] data)
    {

    }
}
