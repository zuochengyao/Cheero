package com.icheero.sdk.core.media.camera;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.util.SparseIntArray;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;

import com.icheero.sdk.core.media.camera.extract.AspectRatio;
import com.icheero.sdk.core.media.camera.extract.BaseCamera;
import com.icheero.sdk.core.media.camera.extract.Size;
import com.icheero.sdk.util.Log;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.SortedSet;

import androidx.annotation.NonNull;

@SuppressLint("MissingPermission")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2 extends BaseCamera
{
    private static final SparseIntArray CAMERA_FACINGS = new SparseIntArray();

    static
    {
        CAMERA_FACINGS.put(FACING_BACK, CameraCharacteristics.LENS_FACING_BACK);
        CAMERA_FACINGS.put(FACING_FRONT, CameraCharacteristics.LENS_FACING_FRONT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) CAMERA_FACINGS.put(FACING_EXTERNAL, CameraCharacteristics.LENS_FACING_EXTERNAL);
    }

    private CameraDevice mCamera;
    private CameraManager mCameraManager;
    private CameraCaptureSession mCameraSession;
    private CameraCharacteristics mCameraCharacteristics;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private SurfaceHolder mSurfaceHolder;
    private ImageReader mImageReader;

    private CameraDevice.StateCallback mCameraDeviceCallback = new CameraDevice.StateCallback()
    {
        @Override
        public void onOpened(@NonNull CameraDevice camera)
        {
            mCamera = camera;
            startCaptureSession();
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
            if (mCamera == null) return;
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
            if (mCameraSession != null && mCameraSession.equals(session)) mCameraSession = null;
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
                    Integer af = result.get(CaptureResult.CONTROL_AF_STATE);
                    if (af == null) break;
                    if (af == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED || af == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED)
                    {
                        Integer ae = result.get(CaptureResult.CONTROL_AE_STATE);
                        if (ae == null || ae == CaptureResult.CONTROL_AE_STATE_CONVERGED)
                        {
                            setCameraState(CAMERA_STATE_CAPTURING);
                            onReady();
                        }
                        else
                        {
                            setCameraState(CAMERA_STATE_LOCKED);
                            onPreCaptureRequired();
                        }
                    }
                }
                case CAMERA_STATE_PRE_CAPTURE:
                {
                    Integer ae = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (ae == null || ae == CaptureResult.CONTROL_AE_STATE_PRECAPTURE || ae == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED || ae == CaptureResult.CONTROL_AE_STATE_CONVERGED)
                        setCameraState(CAMERA_STATE_WAITING);
                    break;
                }
                case CAMERA_STATE_WAITING:
                {
                    Integer ae = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (ae == null || ae != CaptureResult.CONTROL_AE_STATE_PRECAPTURE)
                    {
                        setCameraState(CAMERA_STATE_CAPTURING);
                        onReady();
                    }
                    break;
                }
            }
        }

        private void onReady()
        {
            try
            {
                CaptureRequest.Builder builder = mCamera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                builder.addTarget(getSurface());
                builder.set(CaptureRequest.CONTROL_AF_MODE, mCaptureRequestBuilder.get(CaptureRequest.CONTROL_AF_MODE));
                switch (mFlash)
                {
                    case FLASH_OFF:
                    {
                        builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
                        builder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
                        break;
                    }
                    case FLASH_ON:
                    {
                        builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_ALWAYS_FLASH);
                        break;
                    }
                    case FLASH_TORCH:
                    {
                        builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
                        builder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
                        break;
                    }
                    case FLASH_AUTO:
                    {
                        builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                        break;
                    }
                    case FLASH_RED_EYE:
                    {
                        builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE);
                        break;
                    }
                }
                Integer sensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                if (sensorOrientation != null)
                {
                    // TODO
                    builder.set(CaptureRequest.JPEG_ORIENTATION, (sensorOrientation + mDisplayOrientation * 1));
                }
            }
            catch (CameraAccessException e)
            {
                Log.e(TAG, "Cannot capture a still picture.");
            }
        }

        private void onPreCaptureRequired()
        {
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);
            setCameraState(CAMERA_STATE_PRE_CAPTURE);
            try
            {
                mCameraSession.capture(mCaptureRequestBuilder.build(), this, null);
                mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_IDLE);
            }
            catch (CameraAccessException e)
            {
                Log.e(TAG, "Failed to run preCapture sequence.");
            }
        }
    };

    public Camera2(Activity activity, @NonNull View preview)
    {
        // CameraCharacteristics.LENS_FACING_BACK
        this(activity, preview, FACING_BACK);
    }

    public Camera2(Activity activity, @NonNull View preview, int facing)
    {
        super(activity, preview);
        mCameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        this.mCameraId = CAMERA_FACINGS.get(facing);
    }

    // region BaseCamera
    @Override
    public boolean open()
    {
        if (checkCameraId())
            openCamera();
        return true;
    }

    @Override
    public void close()
    {
        if (mCameraSession != null)
        {
            mCameraSession.close();
            mCameraSession = null;
        }
        if (mCamera != null)
        {
            mCamera.close();
            mCamera = null;
        }
        if (mImageReader != null)
        {
            mImageReader.close();
            mImageReader = null;
        }
    }

    @Override
    public boolean isCameraOpened()
    {
        return mCamera != null;
    }

    @Override
    public void setCameraId(int cameraId)
    {

    }

    @Override
    public int getCameraId()
    {
        return CAMERA_FACINGS.get(mCameraId);
    }

    @Override
    public void setAutoFocus(boolean autoFocus)
    {

    }

    @Override
    public boolean getAutoFocus()
    {
        return false;
    }

    @Override
    public void setFlash(int flash)
    {

    }

    @Override
    public int getFlash()
    {
        return 0;
    }

    @Override
    public void takePicture()
    {

    }

    @Override
    public void setDisplayOrientation(int orientation)
    {

    }
    // endregion

    // region private methods
    private void openCamera()
    {
        initSupportedSize();
        initImageReader();
        try
        {
            String cameraId = String.valueOf(CAMERA_FACINGS.get(mCameraId));
            mCameraManager.openCamera(cameraId, mCameraDeviceCallback, null);
        }
        catch (CameraAccessException e)
        {
            throw new RuntimeException("Failed to open camera: " + mCameraId, e);
        }
    }

    private boolean checkCameraId()
    {
        boolean isSupported = false;
        try
        {
            for (String cameraId : mCameraManager.getCameraIdList())
            {
                mCameraCharacteristics = mCameraManager.getCameraCharacteristics(cameraId);
                Integer level = mCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                if (level != null)
                {
                    // 判断每个CameraId等级，若不等于Full，则返回False
                    if (level != CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL)
                    {
                        isSupported = false;
                        break;
                    }
                    //
                    if (cameraId.equals(String.valueOf(CAMERA_FACINGS.get(mCameraId))))
                        isSupported = true;
                }
                else
                    isSupported = false;
                Log.i(TAG, "cameraId: " + cameraId + ", level: " + level);
            }
        }
        catch (CameraAccessException e)
        {
            isSupported = false;
        }
        return isSupported;
    }

    private void initSupportedSize()
    {
        StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (map == null)
            throw new IllegalStateException("Failed to get configuration map: " + mCameraId);
        mPreviewSizes.clear();
        for (android.util.Size size : map.getOutputSizes(getOutputClass()))
        {
            int w = size.getWidth();
            int h = size.getHeight();
            if (w <= mMaxHeight && h <= mMaxWidth)
                mPreviewSizes.add(new Size(w, h));
        }
        mPictureSizes.clear();
        for (android.util.Size size : map.getOutputSizes(ImageFormat.YUV_420_888))
            mPictureSizes.add(new Size(size.getWidth(), size.getHeight()));
        for (AspectRatio ratio : mPreviewSizes.ratios())
        {
            if (!mPictureSizes.ratios().contains(ratio))
                mPreviewSizes.remove(ratio);
        }
        if (!mPreviewSizes.ratios().contains(mAspectRatio))
            mAspectRatio = mPreviewSizes.ratios().iterator().next();
    }

    private void initImageReader()
    {
        if (mImageReader != null)
            mImageReader.close();
        Size largest = mPictureSizes.get(mAspectRatio).last();
        mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, 2);
        mImageReader.setOnImageAvailableListener(reader -> {
            try (Image image = reader.acquireNextImage())
            {
                Image.Plane[] planes = image.getPlanes();
                if (planes.length > 0)
                {
                    ByteBuffer buffer = planes[0].getBuffer();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    mCallback.onPictureTaken(data);
                }
            }
        }, null);
    }

    private void startCaptureSession()
    {
        if (!isCameraOpened())
            return;
        Size previewSize = chooseOptimalPreviewSize();
        if (mPreview instanceof TextureView)
            mTextureView.getSurfaceTexture().setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
        try
        {
            mCaptureRequestBuilder = mCamera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuilder.addTarget(getSurface());
            mCamera.createCaptureSession(Arrays.asList(getSurface(), mImageReader.getSurface()), mSessionCallback, null);
        }
        catch (CameraAccessException e)
        {
            throw new RuntimeException("Failed to start camera session");
        }
    }

    private Size chooseOptimalPreviewSize()
    {
        SortedSet<Size> previewSizeSet = mPreviewSizes.get(mAspectRatio);
        if (mPreview == null)
            return previewSizeSet.first();
        return previewSizeSet.last();
    }

    // endregion
}
