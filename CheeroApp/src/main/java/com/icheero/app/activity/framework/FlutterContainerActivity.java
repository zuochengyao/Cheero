package com.icheero.app.activity.framework;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;

import io.flutter.facade.Flutter;

public class FlutterContainerActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter_container);
        doInitFlutterView();
    }

    private void doInitFlutterView()
    {
        View flutter = Flutter.createView(this, getLifecycle(), "basicRoute");
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addContentView(flutter, layout);
    }
}
