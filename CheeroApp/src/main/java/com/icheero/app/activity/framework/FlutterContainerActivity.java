package com.icheero.app.activity.framework;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

public class FlutterContainerActivity extends BaseActivity
{
    FlutterView mFlutterView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter_container);
        doInitFlutterView();
        Log.d(TAG, Common.getSignature());
    }

    private void doInitFlutterView()
    {
        mFlutterView = Flutter.createView(this, getLifecycle(), "basicRoute");
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addContentView(mFlutterView, layout);
        method();
    }

    private void method()
    {
        new MethodChannel(mFlutterView, "com.icheero.app.activity/flutter").setMethodCallHandler((methodCall, result) -> {
            if (!TextUtils.isEmpty(methodCall.method))
            {
                Common.toast(this, methodCall.method, Toast.LENGTH_SHORT);
                result.success("success");
            }
            else
                result.notImplemented();
        });
    }
}
