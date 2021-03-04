package com.icheero.sdk.core.storage.file.dir.external;

import android.content.Context;
import android.os.Environment;

import com.icheero.sdk.core.storage.file.FileScopeManager;
import com.icheero.sdk.core.storage.file.dir.DirectoryBase;
import com.icheero.sdk.util.IOUtils;
import com.icheero.sdk.util.Log;

public class FilesDir extends DirectoryBase
{
    @Override
    public void init(Context context)
    {
        Log.i(TAG, "External files dir init");
        Log.i(TAG, "Create folder plugin: " + IOUtils.createDir(context.getExternalFilesDir(null),
                                                                FileScopeManager.DIR_EXTERNAL_FILES_PLUGIN));
        Log.i(TAG, "Create folder image: " + IOUtils.createDir(context.getExternalFilesDir(null),
                                                               FileScopeManager.DIR_EXTERNAL_FILES_IMAGE));
        Log.i(TAG, "Create folder download: " + IOUtils.createDir(context.getExternalFilesDir(null),
                                                                  Environment.DIRECTORY_DOWNLOADS));
        Log.i(TAG, "Create folder video: " + IOUtils.createDir(context.getExternalFilesDir(null),
                                                               FileScopeManager.DIR_EXTERNAL_FILES_VIDEO));
        Log.i(TAG, "Create folder audio: " + IOUtils.createDir(context.getExternalFilesDir(null),
                                                               FileScopeManager.DIR_EXTERNAL_FILES_AUDIO));
        Log.i(TAG, "Create folder log: " + IOUtils.createDir(context.getExternalFilesDir(null),
                                                             FileScopeManager.DIR_EXTERNAL_FILES_LOG));
    }

    @Override
    public void createNewFile(String filename)
    {

    }
}
