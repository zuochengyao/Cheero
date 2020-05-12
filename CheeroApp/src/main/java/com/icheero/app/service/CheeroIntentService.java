package com.icheero.app.service;

import android.content.Intent;

import com.icheero.sdk.base.BaseIntentService;
import com.icheero.sdk.util.Log;

import androidx.annotation.Nullable;

public class CheeroIntentService extends BaseIntentService
{
    public static final String INTENT_TASK = "IntentTask";

    public CheeroIntentService()
    {
        this(CheeroIntentService.class.getName());
    }

    public CheeroIntentService(String name)
    {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        Log.i(TAG, "The worker thread is: " + Thread.currentThread().getName());
        if (intent != null)
        {
            String task = intent.getStringExtra(INTENT_TASK);
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
