package com.icheero.sdk.core.storage.file.dir.internal;

import android.content.Context;

import com.icheero.sdk.core.storage.file.FileScopeManager;
import com.icheero.sdk.core.storage.file.dir.DirectoryBase;
import com.icheero.sdk.util.IOUtils;
import com.icheero.sdk.util.Log;

public class FilesDir extends DirectoryBase
{
    @Override
    public void init(Context context)
    {
        Log.i(TAG, "Internal files dir init");
        Log.i(TAG, "Create folder patch: " + IOUtils.createDir(context.getFilesDir(),
                                                               FileScopeManager.DIR_INTERNAL_FILES_PATCH));
    }

    @Override
    public void createNewFile(String filename)
    {

    }
}
