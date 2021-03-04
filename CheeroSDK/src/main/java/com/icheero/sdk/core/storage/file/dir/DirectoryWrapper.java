package com.icheero.sdk.core.storage.file.dir;

import android.content.Context;

public class DirectoryWrapper extends DirectoryBase
{
    private DirectoryBase mDirectoryBase;

    public DirectoryWrapper(DirectoryBase directoryBase)
    {
        this.mDirectoryBase = directoryBase;
    }

    @Override
    public void init(Context context)
    {
        mDirectoryBase.init(context);
    }

    @Override
    public void createNewFile(String filename)
    {
        mDirectoryBase.createNewFile(filename);
    }
}
