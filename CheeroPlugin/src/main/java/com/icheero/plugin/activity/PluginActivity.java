package com.icheero.plugin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.icheero.plugin.R;
import com.icheero.sdk.base.BaseActivity;

@Route(path = "/plugin/index")
public class PluginActivity extends BaseActivity implements View.OnClickListener
{
    private Button mToCustom;
    private Button mToAndFix;
    private Button mToTinker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doInitView();
    }

    private void doInitView()
    {
        mToCustom = $(R.id.to_custom);
        mToAndFix = $(R.id.to_andfix);
        mToTinker = $(R.id.to_tinker);

        mToCustom.setOnClickListener(this);
        mToAndFix.setOnClickListener(this);
        mToTinker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        Intent intent = null;
        if (id == R.id.to_custom)
            intent = new Intent(this, LoadPluginActivity.class);
        else if (id == R.id.to_andfix)
            intent = new Intent(this, AndFixActivity.class);
        else if (id == R.id.to_tinker)
            intent = new Intent(this, TinkerActivity.class);
        startActivity(intent);
    }
}
