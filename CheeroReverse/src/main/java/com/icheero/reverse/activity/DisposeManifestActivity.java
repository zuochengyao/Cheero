package com.icheero.reverse.activity;

import android.os.Bundle;
import android.view.View;

import com.icheero.reverse.R;
import com.icheero.reverse.manifest.ManifestParser;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.FileUtils;


public class DisposeManifestActivity extends BaseActivity implements View.OnClickListener
{
    private byte[] mManifestData;
    private ManifestParser mManifestParser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_manifest);
        doInitView();
    }

    private void doInitView()
    {
        $(R.id.manifest_dispose).setOnClickListener(this);
        mManifestData = FileUtils.readRawResource(this, R.raw.manifest);
        mManifestParser = new ManifestParser(mManifestData);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == R.id.manifest_dispose)
        {
            mManifestParser.parseHeader();
            mManifestParser.parseStringChunk();
            mManifestParser.parseResourceIdChunk();
            mManifestParser.parseStartNamespaceChunk();
        }
    }
}
