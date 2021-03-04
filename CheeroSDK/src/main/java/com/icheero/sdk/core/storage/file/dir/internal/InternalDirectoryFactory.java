package com.icheero.sdk.core.storage.file.dir.internal;

import com.icheero.sdk.core.storage.file.FileScopeManager;
import com.icheero.sdk.core.storage.file.dir.DirectoryBase;
import com.icheero.sdk.core.storage.file.dir.DirectoryFactory;
import com.icheero.sdk.core.storage.file.dir.DirectoryWrapper;

public class InternalDirectoryFactory extends DirectoryFactory
{
    public DirectoryBase get(String rootDir)
    {
        DirectoryBase mBase;
        switch (rootDir)
        {
            case FileScopeManager.DIR_CACHE:
                mBase = new CacheDir();
                break;
            case FileScopeManager.DIR_FILES:
                mBase = new FilesDir();
                break;
            default:
                mBase = null;
                break;
        }
        return new DirectoryWrapper(mBase);
    }
}
