package com.icheero.plugin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.icheero.plugin.PluginManager;
import com.icheero.plugin.R;
import com.icheero.sdk.base.BaseActivity;

import java.io.File;

@Route(path = "/plugin/main")
public class MainActivity extends BaseActivity implements View.OnClickListener
{
    private Button mToCustom;
    private Button mToMeglive;
    private Button mToTinker;

    private static final String PLUGIN_NAME_MEGLIVE = "meglive.apk";
    private static final String PLUGIN_PACKAGE_MEGLIVE = "com.megvii.meglive_still.demo";
    private static final String PLUGIN_NAME_CHEERO = "CheeroPlugin.apk";
    private static final String PLUGIN_PACKAGE_CHEERO = "com.icheero.plugins";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        doInitView();
    }

    private void doInitView()
    {
        mToCustom = findViewById(R.id.to_custom);
        mToTinker = findViewById(R.id.to_tinker);
        mToMeglive = findViewById(R.id.to_meglive);

        mToCustom.setOnClickListener(this);
        mToTinker.setOnClickListener(this);
        mToMeglive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        Intent intent = null;
        if (id == R.id.to_custom)
            intent = new Intent(this, LoadPluginActivity.class);
        else if (id == R.id.to_tinker)
            intent = new Intent(this, TinkerActivity.class);
        else if (id == R.id.to_meglive)
        {
            File apk = new File(PluginManager.PLUGIN_FILE_PATH);
            intent = new Intent(this, LoadMegliveActivity.class);
        }
        startActivity(intent);
    }
}
