package com.icheero.app.activity.media;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.widget.RelativeLayout;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.media.camera.Camera1;
import com.icheero.sdk.core.media.camera.extract.BaseCamera;
import com.icheero.sdk.core.media.camera.view.SurfacePreview;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurfaceViewActivity extends BaseActivity implements BaseCamera.Callback
{
    @BindView(R.id.surface_root)
    RelativeLayout root;
    SurfacePreview mSurfacePreview;

    private Camera1 mCamera;
    private SurfaceHolder mSurfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
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
        mSurfacePreview = new SurfacePreview(this, root);
        mCamera = new Camera1(this, mSurfacePreview);
        mCamera.open();
        mCamera.setDisplayOrientation(270);

    }

    @Override
    public void onOpened(int width, int height)
    {
        Log.i(TAG, "Camera onOpened:width(" + width + "), height(" + height + ")");
        runOnUiThread(() -> {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSurfacePreview.getView().getLayoutParams();
            params.width = width;
            params.height = height;
            mSurfacePreview.getView().setLayoutParams(params);
        });
    }

    @Override
    public void onClosed()
    {
        Log.i(TAG, "Camera onClosed");
    }

    @Override
    public void onPictureTaken(byte[] data)
    {
        Log.i(TAG, "Camera onPictureTaken");
    }
}
