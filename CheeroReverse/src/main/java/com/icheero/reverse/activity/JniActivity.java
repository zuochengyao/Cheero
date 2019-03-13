package com.icheero.reverse.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.reverse.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.base.CheeroEngine;
import com.icheero.sdk.util.Log;

public class JniActivity extends BaseActivity implements View.OnClickListener
{
    // region Activity Controls
    Button mHelloWorld;
    Button mCallJavaMethod;
    Button mCallJavaNonVirtualMethod;
    Button mGetSystemDataTime;
    Button mCppString;
    Button mCppArray;
    // endregion

    // region 变量
    public String msg = "ABCDE";
    public int number = 2;
    private Father father = new Child();
    int[] intArrays = {4, 3, 12, 56, 1, 23, 45, 67};
    Father[] fatherArrays = {new Father(), new Father(), new Father()};
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        doInitView();
    }

    private void doInitView()
    {
        mHelloWorld = findViewById(R.id.jni_hello_world);
        mCallJavaMethod = findViewById(R.id.jni_call_java_method);
        mCallJavaNonVirtualMethod = findViewById(R.id.jni_call_java_non_virtual_method);
        mGetSystemDataTime = findViewById(R.id.jni_get_system_data_time);
        mCppString = findViewById(R.id.jni_cpp_string);
        mCppArray = findViewById(R.id.jni_cpp_array);
        mHelloWorld.setOnClickListener(this);
        mCallJavaMethod.setOnClickListener(this);
        mCallJavaNonVirtualMethod.setOnClickListener(this);
        mGetSystemDataTime.setOnClickListener(this);
        mCppString.setOnClickListener(this);
        mCppArray.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.jni_hello_world:
            {
                CheeroEngine.nativeHelloWorld();
                break;
            }
            case R.id.jni_call_java_method:
            {
                CheeroEngine.nativeCallJavaMethod(this);
                break;
            }
            case R.id.jni_call_java_non_virtual_method:
            {
                CheeroEngine.nativeCallJavaNonVirtualMethod(this);
                break;
            }
            case R.id.jni_get_system_data_time:
            {
                CheeroEngine.nativeGetSystemDateTime();
                break;
            }
            case R.id.jni_cpp_string:
            {
                CheeroEngine.nativeCppString(this);
                Log.i(TAG, msg);
                break;
            }
            case R.id.jni_cpp_array:
            {
                CheeroEngine.nativeCppArray(this);
                break;
            }
        }
    }


    public int getNumber()
    {
        return number;
    }

    public float summation(float a, float b)
    {
        return a + b;
    }

    public class Father
    {
        public void function()
        {
            Log.i(TAG, "Father function");
        }
    }

    public class Child extends Father
    {
        public void function()
        {
            Log.i(TAG, "Child function");
        }
    }
}
