package com.icheero.plugin.load.proxy;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.icheero.plugin.load.GlobalActivityHookHelper;
import com.icheero.sdk.util.Log;
import com.icheero.sdk.util.RefUtils;

import java.lang.reflect.Field;
import java.util.List;

import androidx.annotation.NonNull;

public class HCallbackProxy implements Handler.Callback
{
    public static final int EXECUTE_TRANSACTION = 159;

    private final Handler mHandler;

    public HCallbackProxy(Handler handler)
    {
        this.mHandler = handler;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg)
    {
        Log.i(HCallbackProxy.class, "Hook handle message: " + msg.what);
        if (msg.what == EXECUTE_TRANSACTION)
        {
            Object r = msg.obj;
            try
            {
                Field mActivityCallbacksField = RefUtils.getDeclaredField(r.getClass(), RefUtils.FILED_M_ACTIVITY_CALLBACKS);
                List<?> mActivityCallbacks = (List<?>) mActivityCallbacksField.get(r);
                if (mActivityCallbacks != null && mActivityCallbacks.size() > 0)
                {
                    for (Object callback : mActivityCallbacks)
                    {
                        if (RefUtils.CLASS_LAUNCH_ACTIVITY_ITEM.equals(callback.getClass().getName()))
                        {
                            Intent pluginIntent = (Intent) RefUtils.getObjectDeclaredFieldValue(callback.getClass(), RefUtils.FILED_M_INTENT, callback);
                            Intent target = pluginIntent.getParcelableExtra(GlobalActivityHookHelper.TARGET_INTENT);
                            pluginIntent.setComponent(target.getComponent());

                        }
                    }
                }
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
