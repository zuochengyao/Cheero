package com.icheero.sdk.core.plugin;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.icheero.sdk.util.Log;
import com.icheero.sdk.util.RefUtils;

import androidx.annotation.NonNull;

public class HookHandlerCallback implements Handler.Callback
{
    public static final int LAUNCH_ACTIVITY = 100;

    private final Handler mHandler;

    public HookHandlerCallback(Handler handler)
    {
        this.mHandler = handler;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg)
    {
        Log.i(HookHandlerCallback.class, "Hook handle message: " + msg.what);
        if (msg.what == LAUNCH_ACTIVITY)
        {
            Object r = msg.obj;
            try
            {
                Intent plugIntent = (Intent) RefUtils.getObjectDeclaredFieldValue(r.getClass(), "intent", r);
                Intent target = plugIntent.getParcelableExtra(HookHelper.TARGET_INTENT);
                plugIntent.setComponent(target.getComponent());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        mHandler.handleMessage(msg);
        return true;
    }
}
