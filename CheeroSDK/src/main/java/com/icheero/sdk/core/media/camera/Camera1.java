package com.icheero.sdk.core.media.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.icheero.sdk.core.media.camera.extract.BaseCamera;
import com.icheero.sdk.core.media.camera.view.BasePreview;
import com.icheero.sdk.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import androidx.collection.SparseArrayCompat;

/**
 * Created by zuochengyao on 2018/2/5.
 */

public class Camera1 extends BaseCamera
{
    private static final Class TAG = Camera1.class;

    private static final SparseIntArray CAMERA_ORIENTATIONS = new SparseIntArray();
    private static final SparseArrayCompat<String> CAMERA_FLASH_MODES = new SparseArrayCompat<>();

    static
    {
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_0, 90);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_90, 0);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_180, 270);
        CAMERA_ORIENTATIONS.append(Surface.ROTATION_270, 180);

        CAMERA_FLASH_MODES.put(BaseCamera.FLASH_OFF, Camera.Parameters.FLASH_MODE_OFF);
        CAMERA_FLASH_MODES.put(BaseCamera.FLASH_ON, Camera.Parameters.FLASH_MODE_ON);
        CAMERA_FLASH_MODES.put(BaseCamera.FLASH_TORCH, Camera.Parameters.FLASH_MODE_TORCH);
        CAMERA_FLASH_MODES.put(BaseCamera.FLASH_AUTO, Camera.Parameters.FLASH_MODE_AUTO);
        CAMERA_FLASH_MODES.put(BaseCamera.FLASH_RED_EYE, Camera.Parameters.FLASH_MODE_RED_EYE);
    }

    private Context mContext;
    private int mCameraId;
    private Camera mCamera;
    private Camera.Parameters mParameters;
    private final Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
    private final SizeMap mPreviewSizes = new SizeMap();
    private final SizeMap mPictureSizes = new SizeMap();
    private AspectRatio mAspectRatio;
    private boolean isShowingPreview = false;
    private boolean isAutoFocus = false;
    private int mFlash;
    private int mDisplayOrientation;

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

    @Override
    public boolean open()
    {
        if (checkCameraId())
        {
            openCamera();
            if (mCamera != null)
            {
                if (mPreview.isReady())
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
    public void setFacing(int facing)
    {

    }

    @Override
    public int getFacing()
    {
        return 0;
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
        return 0;
    }

    @Override
    public void takePicture()
    {

    }

    @Override
    public void setDisplayOrientation(int orientation)
    {
        if (mDisplayOrientation == orientation)
            return;
        mDisplayOrientation = orientation;
        if (isCameraOpened())
            mCamera.setDisplayOrientation(calcDisplayOrientation(orientation));
    }

    private boolean checkCameraId()
    {
        for (int i = 0, count = Camera.getNumberOfCameras(); i < count; i++)
        {
            if (mCameraId == i)
                return true;
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
            mParameters = mCamera.getParameters();
            initSupportedSize();
            adjustCameraParams();
            mCamera.setDisplayOrientation(mDisplayOrientation);
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
        mPreviewSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPreviewSizes())
            mPreviewSizes.add(new Size(size.width, size.height));
        mPictureSizes.clear();
        for (Camera.Size size : mParameters.getSupportedPictureSizes())
            mPictureSizes.add(new Size(size.width, size.height));
        if (mAspectRatio == null)
            mAspectRatio = AspectRatio.DEFAULT;
    }

    /**
     * 调整相机参数
     */
    private void adjustCameraParams()
    {
        SortedSet<Size> sizeSet = mPreviewSizes.get(mAspectRatio);
        // 如果未找到sizeSet，则说明不支持该宽高比
        if (sizeSet == null)
        {
            mAspectRatio = chooseAspectRation();
            sizeSet = mPreviewSizes.get(mAspectRatio);
        }
        Size previewSize = chooseOptimalSize(sizeSet);
        Size pictureSize = mPictureSizes.get(mAspectRatio).last();
        if (isShowingPreview)
            mCamera.stopPreview();
        mParameters.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
        mParameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());
        setAutoFocusInternal(isAutoFocus);
        setFlashInternal(mFlash);
        mCamera.setParameters(mParameters);
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
    private Size chooseOptimalSize(SortedSet<Size> sizeSet)
    {
        if (!mPreview.isReady())
            return sizeSet.first();
        int optimalWidth, optimalHeight;
        final int previewWidth = mPreview.getWidth();
        final int previewHeight = mPreview.getHeight();
        if (isLandscape(mDisplayOrientation))
        {
            optimalWidth = previewHeight;
            optimalHeight = previewWidth;
        }
        else
        {
            optimalWidth = previewWidth;
            optimalHeight = previewHeight;
        }
        Size optimalSize = null;
        for (Size size : sizeSet)
        {
            if (optimalWidth <= size.getWidth() && optimalHeight <= size.getHeight())
                return size;
            optimalSize = size;
        }
        return optimalSize;
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

    private void setUpPreview()
    {
        try
        {
            if (mPreview.getSurfaceClass() == SurfaceHolder.class)
                mCamera.setPreviewDisplay(mPreview.getSurfaceHolder());
            else
                mCamera.setPreviewTexture(mPreview.getSurfaceTexture());
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
}
