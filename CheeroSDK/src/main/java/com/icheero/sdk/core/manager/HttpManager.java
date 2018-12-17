package com.icheero.sdk.core.manager;

import com.icheero.sdk.core.network.http.IHttpManager;
import com.icheero.sdk.core.network.http.framework.okhttp.OkHttpManager;

public class HttpManager
{
    private IHttpManager mHttpManager;

    private static volatile HttpManager mInstance;

    private HttpManager()
    {
        mHttpManager = OkHttpManager.getInstance();
    }

    public static HttpManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpManager.class)
            {
                if (mInstance == null)
                    mInstance = new HttpManager();
            }
        }
        return mInstance;
    }
}
