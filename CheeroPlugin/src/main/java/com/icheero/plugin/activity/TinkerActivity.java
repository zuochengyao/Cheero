package com.icheero.plugin.activity;

import android.os.Bundle;
import android.view.View;

import com.icheero.plugin.R;
import com.icheero.plugin.hotfix.tinker.TinkerManager;
import com.icheero.sdk.base.BaseActivity;


public class TinkerActivity extends BaseActivity
{
    private static final String FILE_EXTENSION = ".apk";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinker);
    }

    public void loadPatch(View view)
    {
        TinkerManager.getInstance().loadPatch("IOUtils.DIR_PATH_CHEERO_PATCHES");
    }
}
