package com.icheero.reverse.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.icheero.reverse.R;
import com.icheero.sdk.base.BaseActivity;

@Route(path = "/reverse/index")
public class ReverseActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
