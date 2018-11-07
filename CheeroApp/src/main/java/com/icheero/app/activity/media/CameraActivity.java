package com.icheero.app.activity.media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.ICamera;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity
{
    public static final String KEY_REQUEST_CODE = "requestCode";
    private ICamera mCamera;
    private File destination;
    private int requestCode = ICamera.REQUEST_CODE_IMAGE;

    @BindView(R.id.button_camera)
    Button cameraButton;
    @BindView(R.id.image_capture)
    ImageView imageView;
    @BindView(R.id.text_file)
    TextView textView;

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
        requestCode = getIntent().getIntExtra("requestCode", ICamera.REQUEST_CODE_IMAGE);
        if (requestCode == ICamera.REQUEST_CODE_IMAGE)
        {
            destination = new File(Environment.getExternalStorageDirectory(), "practice_image.jpg");
            cameraButton.setText("Take a Picture");
        }
        else if (requestCode == ICamera.REQUEST_CODE_VIDEO)
        {
            destination = new File(Environment.getExternalStorageDirectory(), "practice_video.mp4");
            cameraButton.setText("Take a Video");
        }
    }

    @OnClick({R.id.button_camera})
    public void OnClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.button_camera:
            {
                if (requestCode == ICamera.REQUEST_CODE_IMAGE)
                    mCamera.openSystemImageCamera(this, destination);
                else if (requestCode == ICamera.REQUEST_CODE_VIDEO)
                    mCamera.openSystemVideoCamera(this, destination);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case ICamera.REQUEST_CODE_IMAGE:
                {
                    Bitmap image = FileUtils.convertToBitmap(destination);
                    imageView.setImageBitmap(image);
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    break;
                }
                case ICamera.REQUEST_CODE_VIDEO:
                {
                    String location = data.getData().toString();
                    textView.setText(location);
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }
}
