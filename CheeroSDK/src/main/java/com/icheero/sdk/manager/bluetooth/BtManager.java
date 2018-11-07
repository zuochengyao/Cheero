package com.icheero.sdk.manager.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

/**
 * 蓝牙管理
 * Created by zuochengyao on 2018/3/7.
 */

public class BtManager
{
    public static final Class<BtManager> TAG = BtManager.class;

    private BluetoothAdapter mBtAdapter;
    private BluetoothSocket mBtSocket;
    private Context mContext;

    private static volatile BtManager mInstance;

    public static BtManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (BtManager.class)
            {
                if (mInstance == null)
                    mInstance = new BtManager(context);
            }
        }
        return mInstance;
    }

    private BtManager(Context context)
    {
        mContext = context.getApplicationContext();
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    }
}
