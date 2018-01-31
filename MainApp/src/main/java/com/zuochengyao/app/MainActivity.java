package com.zuochengyao.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zuochengyao.sdk.engine.ZcyNative;

public class MainActivity extends AppCompatActivity
{
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.hello_jni);
        ZcyNative zcyNative = new ZcyNative();
        textView.setText(zcyNative.helloWorld());
        zcyNative.updateFileContent("/mnt/sdcard/boy.txt");
    }
}
