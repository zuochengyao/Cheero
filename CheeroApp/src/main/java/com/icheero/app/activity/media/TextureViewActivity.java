package com.icheero.app.activity.media;

import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.media.camera.Camera1;
import com.icheero.sdk.core.media.camera.extract.BaseCamera;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextureViewActivity extends BaseActivity implements TextureView.SurfaceTextureListener, BaseCamera.Callback
{
    @BindView(R.id.texture_view)
    TextureView mTextureView;

    private Camera1 mCamera;
    private int mScreenHeight, mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_texture_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        doInitView();
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
        mCamera = new Camera1(this, mTextureView);
        mCamera.setCallback(this);
        if (!isPortrait())
            mCamera.setDisplayOrientation(90);
        DisplayMetrics dMetrics = getResources().getDisplayMetrics();
        mScreenHeight = dMetrics.heightPixels;
        mScreenWidth = dMetrics.widthPixels;
        mCamera.setMaxSize(mScreenWidth, mScreenHeight);
        mTextureView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)
    {
        Log.i(TAG, "onSurfaceTextureAvailable");
        // 设置预览角度
        if (mCamera != null)
            mCamera.open();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
    {
        Log.i(TAG, "onSurfaceTextureSizeChanged：" + width + " * " + height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
    {
        Log.i(TAG, "surfaceDestroyed");
        if (mCamera != null)
            mCamera.close();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface)
    {

    }

    @Override
    public void onOpened(int width, int height)
    {
        Log.i(TAG, "onOpened：" + width + " * " + height + "");
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTextureView.getLayoutParams();
        if (isPortrait())
        {
            params.width = height;
            params.height = width;
        }
        else
        {
            params.width = width;
            params.height = height;
        }
        mTextureView.post(() -> mTextureView.setLayoutParams(params));
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
