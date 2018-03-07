package com.zcy.app.activity.media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zcy.app.R;
import com.zcy.app.activity.base.BaseActivity;
import com.zcy.sdk.interaction.media.ICamera;
import com.zcy.sdk.util.FileUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity
{
    private ICamera mCamera;
    private File destination;

    @BindView(R.id.button_capture)
    Button captureButton;
    @BindView(R.id.image_capture)
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_camera);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        mCamera = ICamera.getInstance(this);
        destination = new File(Environment.getExternalStorageDirectory(), "practice_image.jpg");
    }

    @OnClick({R.id.button_capture})
    public void OnClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.button_capture:
            {
                mCamera.openSystemCamera(this, destination);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ICamera.REQUEST_CODE_IMAGE && resultCode == RESULT_OK)
        {
            Bitmap image = FileUtils.convertToBitmap(destination);
            imageView.setImageBitmap(image);
        }
    }
}
