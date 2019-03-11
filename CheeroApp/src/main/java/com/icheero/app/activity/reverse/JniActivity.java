package com.icheero.app.activity.reverse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.base.CheeroEngine;
import com.icheero.sdk.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JniActivity extends BaseActivity
{
    // region Activity Controls
    @BindView(R.id.jni_hello_world)
    Button mHelloWorld;
    @BindView(R.id.jni_call_java_method)
    Button mCallJavaMethod;
    @BindView(R.id.jni_call_java_non_virtual_method)
    Button mCallJavaNonVirtualMethod;
    @BindView(R.id.jni_get_system_data_time)
    Button mGetSystemDataTime;
    @BindView(R.id.jni_cpp_string)
    Button mCppString;
    @BindView(R.id.jni_cpp_array)
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
        ButterKnife.bind(this);
    }

    @OnClick({R.id.jni_cpp_array, R.id.jni_cpp_string, R.id.jni_get_system_data_time, R.id.jni_call_java_non_virtual_method, R.id.jni_call_java_method, R.id.jni_hello_world})
    public void OnJniClickEvent(View v)
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
