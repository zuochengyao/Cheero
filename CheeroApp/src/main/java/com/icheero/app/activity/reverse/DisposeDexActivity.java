package com.icheero.app.activity.reverse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.reverse.dex.DexParser;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisposeDexActivity extends BaseActivity
{
    @BindView(R.id.dex_dispose)
    Button mDexDispose;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_dex);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dex_dispose)
    public void onDexDisposeClickEvent(View v)
    {
        byte[] resourceData = FileUtils.readRawResource(this, R.raw.pocdex);
        if (resourceData == null)
        {
            Log.e(TAG, "Read file failed!");
            return;
        }
        new Thread(() -> DexParser.getInstance().parse(resourceData)).start();
    }
}
