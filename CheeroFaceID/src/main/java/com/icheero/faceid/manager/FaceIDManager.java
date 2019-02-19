package com.icheero.faceid.manager;

import com.megvii.api.FaceIDApi;
import com.megvii.api.FaceIDConfig;

public class FaceIDManager
{
    private static volatile FaceIDManager mInstance;

    private FaceIDManager()
    {
        FaceIDConfig config = new FaceIDConfig.Builder()
                .setApiKey("56ymDEP38_Z1uwDoyRSUEUr_ukBJfEzG")
                .setApiSecret("Lmy0aNaQ2fXg_ZaliQtvhjcPrgmxGq5y")
                .setConnectTimeout(20)
                .setReadTimeout(30)
                .setWriteTimeout(30)
                .build();
        FaceIDApi.getInstance().init(config);
    }

    public static FaceIDManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (FaceIDManager.class)
            {
                if (mInstance == null)
                    mInstance = new FaceIDManager();
            }
        }
        return mInstance;
    }
}
