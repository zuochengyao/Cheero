package com.icheero.plugins.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

public class AndFixService extends Service
{
    private static final Class TAG = AndFixService.class;

    private String mPatchFile;
    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_NOT_STICKY;
    }

    private void init()
    {

    }
}
