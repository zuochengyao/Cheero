package com.icheero.sdk.core.media.camera.extract;

public interface ICamera
{
    int FACING_BACK = 0;
    int FACING_FRONT = 1;

    int FLASH_OFF = 0;
    int FLASH_ON = 1;
    int FLASH_TORCH = 2;
    int FLASH_AUTO = 3;
    int FLASH_RED_EYE = 4;

    int LANDSCAPE_90 = 90;
    int LANDSCAPE_270 = 270;

    boolean open();

    void close();

    boolean isCameraOpened();

    void setFacing(int facing);

    int getFacing();

    void setAutoFocus(boolean autoFocus);

    boolean getAutoFocus();

    void setFlash(int flash);

    int getFlash();

    void takePicture();

    void setDisplayOrientation(int orientation);

    interface Callback
    {
        void onOpened();

        void onClosed();

        void onPictureTaken(byte[] data);
    }
}
