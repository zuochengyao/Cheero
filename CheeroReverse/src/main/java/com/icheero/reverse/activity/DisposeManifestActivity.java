package com.icheero.reverse.activity;

import android.os.Bundle;

import com.icheero.reverse.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;


public class DisposeManifestActivity extends BaseActivity
{
    private byte[] mManifestData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_manifest);
        mManifestData = FileUtils.readRawResource(this, R.raw.manifest);
        byte[] magic = Common.copyBytes(mManifestData, 0, 4);
        Log.i(TAG, "magic number:" + Common.byte2HexString(magic));
        byte[] size = Common.copyBytes(mManifestData, 4, 4);
        Log.i(TAG, "xml size:" + Common.byte2HexString(size));
    }

}
