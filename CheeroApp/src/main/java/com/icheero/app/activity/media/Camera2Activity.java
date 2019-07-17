package com.icheero.app.activity.media;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.TextureView;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.media.camera.Camera2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Camera2Activity extends BaseActivity  implements TextureView.SurfaceTextureListener
{
    @BindView(R.id.texture_view)
    TextureView mTextureView;

    private Camera2 camera2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        ButterKnife.bind(this);
        camera2 = new Camera2(this, mTextureView);
        DisplayMetrics dMetrics = getResources().getDisplayMetrics();
        camera2.setMaxSize(dMetrics.widthPixels, dMetrics.heightPixels);


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        camera2.close();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)
    {
        if (camera2 != null)
            camera2.open();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
    {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
    {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface)
    {

    }
}
