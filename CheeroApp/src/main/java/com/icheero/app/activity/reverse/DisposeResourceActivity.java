package com.icheero.app.activity.reverse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisposeResourceActivity extends BaseActivity
{
    @BindView(R.id.resource_dispose)
    Button mResourceDispose;

    private byte[] mResourceData;

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

    }
}
