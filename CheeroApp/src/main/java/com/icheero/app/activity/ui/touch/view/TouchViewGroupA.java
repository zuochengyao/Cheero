package com.icheero.app.activity.ui.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.icheero.sdk.util.Log;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;

public class TouchViewGroupA extends LinearLayout
{
    private final Class<TouchViewGroupA> TAG = TouchViewGroupA.class;
    public TouchViewGroupA(Context context)
    {
        super(context);
    }

    public TouchViewGroupA(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TouchViewGroupA(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.i(TAG, toString() + " dispatchTouchEvent" + ", action: " + ev.getAction());
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        Log.i(TAG, toString() + " onInterceptTouchEvent" + ", action: " + ev.getAction());
        return false;
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
