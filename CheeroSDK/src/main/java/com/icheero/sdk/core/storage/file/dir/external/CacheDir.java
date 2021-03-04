package com.icheero.sdk.core.storage.file.dir.external;

import android.content.Context;

import com.icheero.sdk.core.storage.file.dir.DirectoryBase;
import com.icheero.sdk.util.Log;

public class CacheDir extends DirectoryBase
{
    @Override
    public void init(Context context)
    {
        Log.i(TAG, "External cache dir init");
    }

    @Override
    public void createNewFile(String filename)
    {

    }
}
