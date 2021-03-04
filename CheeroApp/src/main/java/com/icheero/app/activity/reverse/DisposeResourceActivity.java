package com.icheero.app.activity.reverse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.core.reverse.resource.ResourceParser;
import com.icheero.sdk.util.IOUtils;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisposeResourceActivity extends BaseActivity
{
    @BindView(R.id.resource_dispose)
    Button mResourceDispose;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_resource);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.resource_dispose)
    public void onResourceDisposeClickEvent(View v)
    {
        byte[] resourceData = IOUtils.readRawResource(this, R.raw.resources_gdt);
        if (resourceData == null)
        {
            Log.e(TAG, "Read file failed!");
            return;
        }
        IParser mResourceParser = new ResourceParser(resourceData);
        new Thread(mResourceParser::parse).start();
    }
}
