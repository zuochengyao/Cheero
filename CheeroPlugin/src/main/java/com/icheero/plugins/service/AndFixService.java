package com.icheero.plugins.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import androidx.annotation.Nullable;

import com.icheero.network.listener.IResponseListener;
import com.icheero.plugins.model.Patch;
import com.icheero.sdk.core.api.CheeroApi;

import java.lang.ref.WeakReference;

public class AndFixService extends Service
{
    private static final Class TAG = AndFixService.class;
    /** 检查更新 */
    private static final int MESSAGE_PATCH_CHECK_UPDATE = 0x01;
    /** 下载更新 */
    private static final int MESSAGE_PATCH_DOWNLOAD = 0x02;

    private String mPatchFile;
    private AndFixHandler mHandler;

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
        mHandler.sendEmptyMessage(MESSAGE_PATCH_CHECK_UPDATE);
        return START_NOT_STICKY;
    }

    private void init()
    {
        mHandler = new AndFixHandler(this);
    }

    private void checkPatchUpdate()
    {
        CheeroApi.checkPatchUpdate("http://10.155.2.130:8080/cheero/.action", new IResponseListener<Patch>()
        {
            @Override
            public void onSuccess(Patch data)
            {

            }

            @Override
            public void onFailure(int errorCode, String errorMessage)
            {
            }
        });
    }

    static class AndFixHandler extends Handler
    {
        WeakReference<AndFixService> mServiceInstance;

        AndFixHandler(AndFixService serviceInstance)
        {
            this.mServiceInstance = new WeakReference<>(serviceInstance);
        }

        @Override
        public void handleMessage(Message msg)
        {
            final AndFixService service = mServiceInstance.get();
            if (service != null)
            {
                switch (msg.what)
                {
                    case MESSAGE_PATCH_CHECK_UPDATE:
                    {
                        service.checkPatchUpdate();
                        break;
                    }
                }
            }
        }
    }
}
