package com.icheero.sdk.core.storage.file.dir.external;

import com.icheero.sdk.core.storage.file.FileScopeManager;
import com.icheero.sdk.core.storage.file.dir.DirectoryBase;
import com.icheero.sdk.core.storage.file.dir.DirectoryFactory;
import com.icheero.sdk.core.storage.file.dir.DirectoryWrapper;

public class ExternalDirectoryFactory extends DirectoryFactory
{
    @Override
    public DirectoryBase get(String rootDir)
    {
        DirectoryBase mBase;
        switch (rootDir)
        {
            case FileScopeManager.DIR_FILES:
            case FileScopeManager.DIR_EXTERNAL_FILES_IMAGE:
            case FileScopeManager.DIR_EXTERNAL_FILES_VIDEO:
            case FileScopeManager.DIR_EXTERNAL_FILES_AUDIO:
            case FileScopeManager.DIR_EXTERNAL_FILES_PLUGIN:
            case FileScopeManager.DIR_EXTERNAL_FILES_LOG:
                mBase = new FilesDir();
                break;
            case FileScopeManager.DIR_CACHE:
                mBase = new CacheDir();
                break;
            default:
                mBase = null;
                break;
        }
        return new DirectoryWrapper(mBase);
    }
}
