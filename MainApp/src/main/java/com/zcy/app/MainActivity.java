package com.zcy.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zcy.sdk.engine.JniNative;

public class MainActivity extends AppCompatActivity
{
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.hello_jni);
        JniNative.serviceSetTraceMode(0);
        JniNative.serviceSizeOfDataType();
    }
}
