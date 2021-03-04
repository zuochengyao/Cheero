package com.icheero.sdk.core.storage.file.dir;

import android.content.Context;

public abstract class DirectoryBase
{
    protected Class<?> TAG;

    public DirectoryBase()
    {
        TAG = getClass();
    }

    public abstract void init(Context context);

    public abstract void createNewFile(String filename);
}
