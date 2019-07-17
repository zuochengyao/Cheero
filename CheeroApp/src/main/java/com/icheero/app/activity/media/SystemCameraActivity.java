package com.icheero.app.activity.media;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.CameraManager;
import com.icheero.sdk.util.FileUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemCameraActivity extends BaseActivity
{
    public static final String KEY_REQUEST_CODE = "requestCode";
    private File destination;
    private int requestCode = CameraManager.REQUEST_CODE_IMAGE;

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
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        requestCode = getIntent().getIntExtra("requestCode", CameraManager.REQUEST_CODE_IMAGE);
        if (requestCode == CameraManager.REQUEST_CODE_IMAGE)
        {
            destination = new File(FileUtils.DIR_PATH_CHEERO_IMAGES, "practice_image.jpg");
            cameraButton.setText("Take a Picture");
        }
        else if (requestCode == CameraManager.REQUEST_CODE_VIDEO)
        {
            destination = new File(FileUtils.DIR_PATH_CHEERO_VIDEOS, "practice_video.mp4");
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
                if (!mPermissionManager.checkPermission(Manifest.permission.CAMERA))
                    mPermissionManager.permissionRequest(Manifest.permission.CAMERA);
                else
                    openSystemCamera();
                break;
            }
        }
    }

    private void openSystemCamera()
    {
        if (requestCode == CameraManager.REQUEST_CODE_IMAGE)
            CameraManager.getInstance().openSystemImageCamera(this, destination);
        else if (requestCode == CameraManager.REQUEST_CODE_VIDEO)
            CameraManager.getInstance().openSystemVideoCamera(this, destination);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case CameraManager.REQUEST_CODE_IMAGE:
                {
                    Bitmap image = FileUtils.convertToBitmap(destination);
                    imageView.setImageBitmap(image);
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    break;
                }
                case CameraManager.REQUEST_CODE_VIDEO:
                {
                    String location = String.valueOf(data.getData());
                    textView.setText(location);
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    @Override
    public void onPermissionRequest(boolean isGranted, String permission)
    {
        super.onPermissionRequest(isGranted, permission);
        if (isGranted)
            openSystemCamera();
    }
}
