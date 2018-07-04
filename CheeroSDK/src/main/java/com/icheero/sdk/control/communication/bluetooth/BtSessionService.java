package com.icheero.sdk.control.communication.bluetooth;

import android.bluetooth.BluetoothAdapter;

import com.icheero.common.util.Log;

import java.util.UUID;
import java.util.logging.Handler;

/**
 * Created by zuochengyao on 2018/3/7.
 */

public class BtSessionService
{
    private static final Class<BtSessionService> TAG = BtSessionService.class;
    public static final UUID BT_UUID = UUID.fromString("3b2cebf7-7b64-4856-b04c-679e4cddf413");
    private static final String BT_SEARCH_NAME = "bluetooth.recipe";

    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;

    public BtSessionService(Handler handler)
    {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mHandler = handler;
    }

    public synchronized void start()
    {
        Log.i(TAG, "Bluetooth session start!");
    }

    public interface OnAcceptThreadEndListener
    {
        void onAcceptThreadEnd(boolean isOk);
    }

    private class AcceptThread extends Thread
    {
        private OnAcceptThreadEndListener listener;
        //private final BluetoothServerSocket mServerSocket;
        private boolean isWaitingNext = false;
        public AcceptThread()
        {

        }
    }
}
