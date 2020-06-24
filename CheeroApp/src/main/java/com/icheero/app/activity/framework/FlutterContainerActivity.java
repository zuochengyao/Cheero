package com.icheero.app.activity.framework;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.icheero.app.R;
import com.icheero.sdk.util.Common;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;

public class FlutterContainerActivity extends FlutterActivity
{
    FlutterView mFlutterView;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine)
    {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor(), "com.icheero.app.activity/fta").setMethodCallHandler((methodCall, result) -> {
            if (!TextUtils.isEmpty(methodCall.method))
            {
                Common.toast(this, methodCall.method, Toast.LENGTH_SHORT);
                result.success("success");
            }
            else
                result.notImplemented();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter_container);

        doInitFlutterView();
    }

    private void doInitFlutterView()
    {
        mFlutterView = new FlutterView(this);
        mFlutterView.setInitialRoute("basicRoute");
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addContentView(mFlutterView, params);
        event();
        method();
    }

    private void method()
    {
        new MethodChannel(mFlutterView, "com.icheero.app.activity/fta").setMethodCallHandler((methodCall, result) -> {
            if (!TextUtils.isEmpty(methodCall.method))
            {
                Common.toast(this, methodCall.method, Toast.LENGTH_SHORT);
                result.success("success");
            }
            else
            {
                result.notImplemented();
            }
        });
    }

    private void event()
    {
        new EventChannel(mFlutterView, "com.icheero.app.activity/atf").setStreamHandler(new EventChannel.StreamHandler()
        {
            @Override
            public void onListen(Object o, EventChannel.EventSink eventSink)
            {
                String param = "From Android Data";
                eventSink.success(param);
            }

            @Override
            public void onCancel(Object o)
            {
            }
        });
    }
}
