package com.icheero.sdk.core.media.video;

import android.media.MediaRecorder;

import com.icheero.sdk.core.media.camera.extract.BaseCamera;

public class VideoRecorder
{
    private BaseCamera mCamera;
    private MediaRecorder mMediaRecorder;

    public VideoRecorder(BaseCamera camera)
    {
        this.mCamera = camera;
        this.mMediaRecorder = new MediaRecorder();
    }

    public void startRecord()
    {

    }

    public void stopRecord()
    {

    }
}
