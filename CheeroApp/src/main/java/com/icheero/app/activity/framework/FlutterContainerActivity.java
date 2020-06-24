package com.icheero.app.activity.framework;

import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;

//import io.flutter.facade.Flutter;
//import io.flutter.plugin.common.EventChannel;
//import io.flutter.plugin.common.MethodChannel;
//import io.flutter.view.FlutterView;

public class FlutterContainerActivity extends BaseActivity
{
//    FlutterView mFlutterView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter_container);
        doInitFlutterView();
    }

    private void doInitFlutterView()
    {
//        mFlutterView = Flutter.createView(this, getLifecycle(), "basicRoute");
//        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        addContentView(mFlutterView, layout);
//        event();
//        method();
    }

    private void method()
    {
//        new MethodChannel(mFlutterView, "com.icheero.app.activity/fta").setMethodCallHandler((methodCall, result) -> {
//            if (!TextUtils.isEmpty(methodCall.method))
//            {
//                Common.toast(this, methodCall.method, Toast.LENGTH_SHORT);
//                result.success("success");
//            }
//            else
//            {
//                result.notImplemented();
//            }
//        });
    }

    private void event()
    {
//        new EventChannel(mFlutterView, "com.icheero.app.activity/atf").setStreamHandler(new EventChannel.StreamHandler()
//        {
//            @Override
//            public void onListen(Object o, EventChannel.EventSink eventSink)
//            {
//                String param = "From Android Data";
//                eventSink.success(param);
//            }
//
//            @Override
//            public void onCancel(Object o)
//            {
//            }
//        });
    }
}
