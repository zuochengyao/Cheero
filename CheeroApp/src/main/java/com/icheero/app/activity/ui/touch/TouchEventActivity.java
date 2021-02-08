package com.icheero.app.activity.ui.touch;

import android.os.Bundle;
import android.view.MotionEvent;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.Log;

import org.jetbrains.annotations.NotNull;

public class TouchEventActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.i(TAG, toString() + " dispatchTouchEvent" + ", action: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        Log.i(TAG, toString() + " onTouchEvent" + ", action: " + ev.getAction());
        return super.onTouchEvent(ev);
    }

    @NotNull
    @Override
    public String toString()
    {
        return getClass().getSimpleName();
    }
}