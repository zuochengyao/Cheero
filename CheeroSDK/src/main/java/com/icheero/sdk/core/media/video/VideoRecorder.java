package com.icheero.sdk.core.media.video;

import android.media.MediaRecorder;

import com.icheero.sdk.core.media.camera.extract.ICamera;

public class VideoRecorder
{
    private ICamera mCamera;
    private MediaRecorder mMediaRecorder;

    public VideoRecorder(ICamera camera)
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
