package com.icheero.app.activity.framework;

import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;

public class EventBusActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
    }
}
