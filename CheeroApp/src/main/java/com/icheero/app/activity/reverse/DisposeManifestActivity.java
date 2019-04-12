package com.icheero.app.activity.reverse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.core.reverse.manifest.ManifestParser;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DisposeManifestActivity extends BaseActivity
{
    @BindView(R.id.manifest_dispose_1)
    Button mManifest1;
    @BindView(R.id.manifest_dispose_2)
    Button mManifest2;

    private byte[] mManifestData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_manifest);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.manifest_dispose_1, R.id.manifest_dispose_2})
    public void onManifestDisposeClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.manifest_dispose_1:
                mManifestData = FileUtils.readRawResource(this, R.raw.manifest);
                break;
            case R.id.manifest_dispose_2:
                mManifestData = FileUtils.readRawResource(this, R.raw.manifest2);
                break;
        }
        if (mManifestData == null)
        {
            Log.e(TAG, "Read file failed!");
            return;
        }
        IParser mManifestParser = new ManifestParser(mManifestData);
        mManifestParser.parse();
    }
}
