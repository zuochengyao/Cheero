package com.icheero.sdk.core.media.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;

import com.icheero.sdk.core.media.camera.extract.AspectRatio;
import com.icheero.sdk.core.media.camera.extract.BaseCamera;
import com.icheero.sdk.core.media.camera.extract.Size;
import com.icheero.sdk.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.collection.SparseArrayCompat;

/**
 * Created by zuochengyao on 2018/2/5.
 */

public class Camera1 extends BaseCamera
{
    private static final SparseIntArray CAMERA_ORIENTATIONS = new SparseIntArray();
    private static final SparseArrayCompat<String> CAMERA_FLASH_MODES = new SparseArrayCompat<>();

    static
    {
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_0, 0);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_90, 90);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_180, 180);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_270, 270);

        CAMERA_FLASH_MODES.put(FLASH_OFF, Camera.Parameters.FLASH_MODE_OFF);
        CAMERA_FLASH_MODES.put(FLASH_ON, Camera.Parameters.FLASH_MODE_ON);
        CAMERA_FLASH_MODES.put(FLASH_TORCH, Camera.Parameters.FLASH_MODE_TORCH);
        CAMERA_FLASH_MODES.put(FLASH_AUTO, Camera.Parameters.FLASH_MODE_AUTO);
        CAMERA_FLASH_MODES.put(FLASH_RED_EYE, Camera.Parameters.FLASH_MODE_RED_EYE);
    }

    private Camera mCamera;
    private Camera.Parameters mParameters;
    private final Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
    private boolean isShowingPreview = false;

    private final AtomicBoolean isPictureCaptureInProgress = new AtomicBoolean(false);

    /*
    public Camera1(Callback callback, BasePreview preview)
    {
        this(callback, preview, Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public Camera1(Callback callback, BasePreview preview, int cameraId)
    {
        super(callback, preview);
        this.mCameraId = cameraId;
        preview.setCallback(() -> {
            if (mCamera != null)
            {
                setUpPreview();
                adjustCameraParams();
            }
        });
    }
    */

    public Camera1(Activity activity, View preview)
    {
        this(activity, preview, Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public Camera1(Activity activity, View preview, int cameraId)
    {
        super(activity, preview);
        this.mCameraId = cameraId;
    }

    // region BaseCamera
    @Override
    public boolean open()
    {
        if (checkCameraId())
        {
            openCamera();
            if (mCamera != null)
            {
                if (mPreview != null)
                    setUpPreview();
                mCamera.startPreview();
                isShowingPreview = true;
                if (mCallback != null)
                    mCallback.onOpened(mParameters.getPreviewSize().width, mParameters.getPreviewSize().height);
            }
        }
        return isShowingPreview;
    }

    @Override
    public void close()
    {
        if (mCamera != null)
            mCamera.stopPreview();
        isShowingPreview = false;
        releaseCamera();
    }

    @Override
    public boolean isCameraOpened()
    {
        return mCamera != null;
    }

    @Override
    public void setCameraId(int cameraId)
    {
        if (mCameraId == cameraId)
            return;
        mCameraId = cameraId;
        if (isCameraOpened())
        {
            close();
            open();
        }
    }

    @Override
    public int getCameraId()
    {
        return mCameraId;
    }

    @Override
    public void setAutoFocus(boolean autoFocus)
    {
        if (isAutoFocus == autoFocus)
            return;
        if (setAutoFocusInternal(autoFocus))
            mCamera.setParameters(mParameters);
    }

    @Override
    public boolean getAutoFocus()
    {
        if (!isCameraOpened())
            return false;
        return mParameters.getFocusMode() != null && mParameters.getFocusMode().contains("continuous");
    }

    @Override
    public void setFlash(int flash)
    {
        if (mFlash == flash)
            return;
        if (setFlashInternal(flash))
            mCamera.setParameters(mParameters);
    }

    @Override
    public int getFlash()
    {
        return mFlash;
    }

    @Override
    public void takePicture()
    {
        if (!isCameraOpened())
            return;
        if (getAutoFocus())
        {
            mCamera.cancelAutoFocus();
            mCamera.autoFocus((success, camera) -> takePictureInternal());
        }
        else
            takePictureInternal();
    }
    // endregion

    /**
     * 如果设置为90 or 270，则认为是横屏
     *
     * @param orientation 角度
     */
    @Override
    public void setDisplayOrientation(int orientation)
    {
        if (mDisplayOrientation == orientation)
            return;
        mDisplayOrientation = orientation;
        if (isCameraOpened())
        {
            mParameters.setRotation(calcCameraRotation(orientation));
            mCamera.setParameters(mParameters);
            mCamera.setDisplayOrientation(calcDisplayOrientation());
        }
    }

    private boolean checkCameraId()
    {
        for (int i = 0, count = Camera.getNumberOfCameras(); i < count; i++)
        {
            if (mCameraId == i)
            {
                Camera.getCameraInfo(i, mCameraInfo);
                return true;
            }
        }
        return false;
    }

    private void openCamera()
    {
        if (mCamera != null)
            releaseCamera();
        try
        {
            mCamera = Camera.open(mCameraId);
            initSupportedSize();
            adjustCameraParams();
        }
        catch (Exception e)
        {
            Log.e(TAG, "Open camera failed. CameraId = " + mCameraId);
        }
    }

    private void releaseCamera()
    {
        if (mCamera != null)
        {
            mCamera.release();
            mCamera = null;
            if (mCallback != null)
                mCallback.onClosed();
        }
    }

    /**
     * 检测相机支持的Size
     */
    private void initSupportedSize()
    {
        if (mCamera == null)
            return;
        mParameters = mCamera.getParameters();
        mPreviewSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPreviewSizes())
            mPreviewSizes.add(new Size(size.width, size.height));
        mPictureSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPictureSizes())
            mPictureSizes.add(new Size(size.width, size.height));
    }

    /**
     * 调整相机参数
     */
    private void adjustCameraParams()
    {
        SortedSet<Size> previewSizeSet = mPreviewSizes.get(mAspectRatio);
        // 如果未找到sizeSet，则说明不支持该宽高比
        if (previewSizeSet == null)
        {
            mAspectRatio = chooseAspectRation();
            previewSizeSet = mPreviewSizes.get(mAspectRatio);
        }
        Size previewSize = chooseOptimalPreviewSize(previewSizeSet);
        // TODO 选择最佳合适picture size
        Size pictureSize = mPictureSizes.get(mAspectRatio).last();
        if (isShowingPreview)
            mCamera.stopPreview();
        mParameters.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
        mParameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());
        mParameters.setRotation(calcCameraRotation(mDisplayOrientation));
        setAutoFocusInternal(isAutoFocus);
        setFlashInternal(mFlash);
        mCamera.setParameters(mParameters);
        mCamera.setDisplayOrientation(calcDisplayOrientation());
        if (isShowingPreview)
            mCamera.startPreview();
    }

    /**
     * 选择合适的宽高比
     * @return AspectRatio实例
     */
    private AspectRatio chooseAspectRation()
    {
        AspectRatio ratio = null;
        for (AspectRatio r : mPreviewSizes.ratios())
        {
            ratio = r;
            if (ratio.equals(AspectRatio.DEFAULT))
                return r;
        }
        return ratio;
    }

    /**
     * 选择最佳Size
     * @param sizeSet Size集合
     * @return Size
     */
    private Size chooseOptimalPreviewSize(SortedSet<Size> sizeSet)
    {
        if (mPreview == null)
            return sizeSet.first();
        Size optimalSize = sizeSet.last();
        for (Size size : sizeSet)
        {
            if (size.getWidth() <= mMaxHeight && size.getHeight() <= mMaxWidth)
                optimalSize = size;
            else
                break;
        }
        Log.i(TAG, "The OptimalSize is " + optimalSize.toString());
        return optimalSize;
    }

    private int calcDisplayOrientation()
    {
        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        int degree = CAMERA_ORIENTATIONS.get(rotation);
        return calcDisplayOrientation(degree);
    }

    private int calcDisplayOrientation(int screenOrientationDegrees)
    {
        if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
            return (360 - (mCameraInfo.orientation + screenOrientationDegrees) % 360) % 360;
        else if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
            return (mCameraInfo.orientation - screenOrientationDegrees + 360) % 360;
        else
            return screenOrientationDegrees;
    }

    private int calcCameraRotation(int screenOrientationDegrees)
    {
        if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
            return (mCameraInfo.orientation + screenOrientationDegrees) % 360;
        else if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
        {
            final int landscapeFlip = isLandscape(screenOrientationDegrees) ? 180 : 0;
            return (mCameraInfo.orientation + screenOrientationDegrees + landscapeFlip) % 360;
        }
        else
            return screenOrientationDegrees;
    }

    private void setUpPreview()
    {
        try
        {
            if (mPreview instanceof SurfaceView)
                mCamera.setPreviewDisplay(mSurfaceView.getHolder());
            else if (mPreview instanceof TextureView)
                mCamera.setPreviewTexture(mTextureView.getSurfaceTexture());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private boolean setAutoFocusInternal(boolean autoFocus)
    {
        isAutoFocus = autoFocus;
        if (isCameraOpened())
        {
            final List<String> modes = mParameters.getSupportedFocusModes();
            if (autoFocus && modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE))
                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            else if (modes.contains(Camera.Parameters.FOCUS_MODE_FIXED))
                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
            else if (modes.contains(Camera.Parameters.FOCUS_MODE_INFINITY))
                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
            else
                mParameters.setFocusMode(modes.get(0));
            return true;
        }
        else
            return false;
    }

    private boolean setFlashInternal(int flash)
    {
        if (isCameraOpened())
        {
            List<String> modes = mParameters.getSupportedFlashModes();
            String mode = CAMERA_FLASH_MODES.get(flash);
            if (modes != null && modes.contains(mode))
            {
                mParameters.setFlashMode(mode);
                mFlash = flash;
                return true;
            }
            String currentMode = CAMERA_FLASH_MODES.get(mFlash);
            if (modes == null || !modes.contains(currentMode))
            {
                mParameters.setFlashMode(currentMode);
                mFlash = FLASH_OFF;
                return true;
            }
            return false;
        }
        else
        {
            mFlash = flash;
            return false;
        }
    }

    private boolean isLandscape(int orientationDegrees)
    {
        return (orientationDegrees == LANDSCAPE_90 || orientationDegrees == LANDSCAPE_270);
    }

    private void takePictureInternal()
    {
        if (!isPictureCaptureInProgress.getAndSet(true))
        {
            mCamera.takePicture(null, null, null, (data, camera) ->
            {
                isPictureCaptureInProgress.set(false);
                mCallback.onPictureTaken(data);
                camera.cancelAutoFocus();
                camera.startPreview();
            });
        }
    }
}
