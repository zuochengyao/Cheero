package com.icheero.sdk.base;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.icheero.sdk.util.Log;

import androidx.annotation.Nullable;

public abstract class BaseService extends Service
{
    protected Class TAG;
    protected BaseService mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        TAG = getClass();
        Log.i(TAG, TAG.getSimpleName() + " onCreate");
        mInstance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i(TAG, TAG.getSimpleName() + " onStartCommand flags = " + flags + ", startId = " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, TAG.getSimpleName() + " onDestroy");
        mInstance = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.i(TAG, TAG.getSimpleName() + " onBind, intent = " + intent.toString());
        return getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.i(TAG, TAG.getSimpleName() + " onUnbind, intent = " + intent.toString());
        return super.onUnbind(intent);
    }

    protected abstract Binder getBinder();
}
