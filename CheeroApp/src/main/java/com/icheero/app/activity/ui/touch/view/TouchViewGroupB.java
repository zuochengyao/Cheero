package com.icheero.app.activity.ui.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.icheero.sdk.util.Log;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;

public class TouchViewGroupB extends LinearLayout
{
    private final Class<TouchViewGroupB> TAG = TouchViewGroupB.class;
    public TouchViewGroupB(Context context)
    {
        super(context);
    }

    public TouchViewGroupB(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TouchViewGroupB(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.i(TAG, toString() + " dispatchTouchEvent" + ", action: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        Log.i(TAG, toString() + " onInterceptTouchEvent" + ", action: " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
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
        return "TouchViewGroupB";
    }
}
