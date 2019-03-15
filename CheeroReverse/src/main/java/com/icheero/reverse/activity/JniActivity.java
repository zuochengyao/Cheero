package com.icheero.reverse.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.reverse.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.base.CheeroNative;
import com.icheero.sdk.util.Log;

public class JniActivity extends BaseActivity implements View.OnClickListener
{
    // region Activity Controls
    Button mCheckEndian;
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
        mCheckEndian = $(R.id.jni_check_endian);
        mHelloWorld = $(R.id.jni_hello_world);
        mCallJavaMethod = $(R.id.jni_call_java_method);
        mCallJavaNonVirtualMethod = $(R.id.jni_call_java_non_virtual_method);
        mGetSystemDataTime = $(R.id.jni_get_system_data_time);
        mCppString = $(R.id.jni_cpp_string);
        mCppArray = $(R.id.jni_cpp_array);
        mCheckEndian.setOnClickListener(this);
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
        int i = v.getId();
        if (i == R.id.jni_check_endian)
            CheeroNative.nativeCheckEndian();
        else if (i == R.id.jni_hello_world)
            CheeroNative.nativeHelloWorld();
        else if (i == R.id.jni_call_java_method)
            CheeroNative.nativeCallJavaMethod(this);
        else if (i == R.id.jni_call_java_non_virtual_method)
            CheeroNative.nativeCallJavaNonVirtualMethod(this);
        else if (i == R.id.jni_get_system_data_time)
            CheeroNative.nativeGetSystemDateTime();
        else if (i == R.id.jni_cpp_string)
        {
            CheeroNative.nativeCppString(this);
            Log.i(TAG, msg);
        }
        else if (i == R.id.jni_cpp_array)
            CheeroNative.nativeCppArray(this);
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
