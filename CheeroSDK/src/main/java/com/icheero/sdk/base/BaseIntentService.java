package com.icheero.sdk.base;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import com.icheero.sdk.util.Log;

import androidx.annotation.Nullable;

public abstract class BaseIntentService extends IntentService
{
    protected Class TAG;
    protected BaseIntentService mInstance;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BaseIntentService(String name)
    {
        super(name);
    }

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
        Log.i(TAG, TAG.getSimpleName() + " onStartCommand, startId = " + startId);
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
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.i(TAG, TAG.getSimpleName() + " onUnbind, intent = " + intent.toString());
        return super.onUnbind(intent);
    }
}
