package com.icheero.reverse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.icheero.reverse.R;
import com.icheero.sdk.base.BaseActivity;

@Route(path = "/reverse/index")
public class ReverseActivity extends BaseActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse);
        doInitView();
    }

    private void doInitView()
    {
        $(R.id.to_jni_activity).setOnClickListener(this);
        $(R.id.to_dispose_so_activity).setOnClickListener(this);
        $(R.id.to_dispose_manifest_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        int i = v.getId();
        if (i == R.id.to_jni_activity)
            intent.setClass(this, JniActivity.class);
        else if (i == R.id.to_dispose_so_activity)
            intent.setClass(this, DisposeSoActivity.class);
        else if (i == R.id.to_dispose_manifest_activity)
            intent.setClass(this, DisposeManifestActivity.class);
        startActivity(intent);
    }
}
