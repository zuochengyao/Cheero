package com.icheero.app.activity.reverse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.reverse.so.SoParser;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisposeSoActivity extends BaseActivity
{
    @BindView(R.id.so_dispose)
    Button mSoDispose;

    private SoParser mSoParser;
    private byte[] mSoData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_so);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.so_dispose)
    public void onSoDisposeClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.so_dispose:
                mSoData = FileUtils.readRawResource(this, R.raw.libhello);
                if (mSoData == null)
                {
                    Log.e(TAG, "Read file failed!");
                    return;
                }
                mSoParser = new SoParser(mSoData);
                mSoParser.parse();
                break;
        }
    }
}
