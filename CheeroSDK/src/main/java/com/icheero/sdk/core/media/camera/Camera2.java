package com.icheero.sdk.core.media.camera;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.util.SparseIntArray;
import android.view.SurfaceHolder;
import android.view.View;

import com.icheero.sdk.core.media.camera.extract.BaseCamera;
import com.icheero.sdk.util.Log;

import androidx.annotation.NonNull;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2 extends BaseCamera
{
    private static final SparseIntArray CAMERA_FACINGS = new SparseIntArray();

    static
    {
        CAMERA_FACINGS.put(FACING_BACK, CameraCharacteristics.LENS_FACING_BACK);
        CAMERA_FACINGS.put(FACING_FRONT, CameraCharacteristics.LENS_FACING_FRONT);
    }

    // Camera2 最大预览宽度
    private static final int MAX_PREVIEW_WIDTH = 1920;
    // Camera2 最大预览高度
    private static final int MAX_PREVIEW_HEIGHT = 1080;

    private CameraDevice mCamera;
    private CameraManager mCameraManager;
    private CameraCaptureSession mCameraSession;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private SurfaceHolder mSurfaceHolder;

    private CameraDevice.StateCallback mCameraDeviceCallback = new CameraDevice.StateCallback()
    {
        @Override
        public void onOpened(@NonNull CameraDevice camera)
        {
            mCamera = camera;
            // mCallback.onOpened(camera.);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera)
        {
            mCallback.onClosed();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error)
        {
            Log.e(TAG, "onError: " + camera.getId() + " (" + error + ")");
            mCamera = null;
        }
    };

    private CameraCaptureSession.StateCallback mSessionCallback = new CameraCaptureSession.StateCallback()
    {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession session)
        {
            if (mCamera == null)
                return;
            mCameraSession = session;
            // updateAutoFocus();
            // updateFlash();
            try
            {
                mCameraSession.setRepeatingRequest(mCaptureRequestBuilder.build(), mCaptureCallback, null);
            }
            catch (CameraAccessException e)
            {
                Log.e(TAG, "Failed to start camera preview because it couldn't access camera.");
            }
            catch (IllegalStateException e)
            {
                Log.e(TAG, "Failed to start camera preview.");
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session)
        {
            Log.e(TAG, "Failed to configure capture session.");
        }

        @Override
        public void onClosed(@NonNull CameraCaptureSession session)
        {
            if (mCameraSession != null && mCameraSession.equals(session))
                mCameraSession = null;
        }
    };

    private CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback()
    {
        static final int CAMERA_STATE_PREVIEW = 0;
        static final int CAMERA_STATE_LOCKING = 1;
        static final int CAMERA_STATE_LOCKED = 2;
        static final int CAMERA_STATE_PRE_CAPTURE = 3;
        static final int CAMERA_STATE_WAITING = 4;
        static final int CAMERA_STATE_CAPTURING = 5;

        private int mCameraState = CAMERA_STATE_PREVIEW;

        void setCameraState(int cameraState)
        {
            this.mCameraState = cameraState;
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult)
        {
            process(partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result)
        {
            process(result);
        }

        private void process(@NonNull CaptureResult result)
        {
            switch (mCameraState)
            {
                case CAMERA_STATE_LOCKING:
                {
                    break;
                }
                case CAMERA_STATE_PRE_CAPTURE:
                {
                    break;
                }

                case CAMERA_STATE_WAITING:
                {
                    break;
                }
            }
        }
    };

    public Camera2(Activity activity, @NonNull View preview, int cameraId)
    {
        super(activity, preview, cameraId);
        mCameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
    }

    // region BaseCamera
    @Override
    protected boolean open()
    {
        return false;
    }

    @Override
    protected void close()
    {

    }

    @Override
    protected boolean isCameraOpened()
    {
        return false;
    }

    @Override
    protected void setFacing(int facing)
    {

    }

    @Override
    protected int getFacing()
    {
        return 0;
    }

    @Override
    protected void setAutoFocus(boolean autoFocus)
    {

    }

    @Override
    protected boolean getAutoFocus()
    {
        return false;
    }

    @Override
    protected void setFlash(int flash)
    {

    }

    @Override
    protected int getFlash()
    {
        return 0;
    }

    @Override
    protected void takePicture()
    {

    }

    @Override
    protected void setDisplayOrientation(int orientation)
    {

    }
    // endregion


}
