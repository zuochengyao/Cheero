package com.icheero.app.service;

import android.os.Binder;

import com.icheero.sdk.base.BaseService;
import com.icheero.sdk.util.Log;

public class CheeroService extends BaseService
{
    public static final String ACTION_START = "com.icheero.app.service.CheeroService";

    @Override
    protected Binder getBinder()
    {
        return new CheeroBinder();
    }

    public void call()
    {
        Log.i(TAG, "Call CheeroService Success!");
    }

    public class CheeroBinder extends Binder
    {
        public CheeroService getService()
        {
            return CheeroService.this;
        }
    }
}
