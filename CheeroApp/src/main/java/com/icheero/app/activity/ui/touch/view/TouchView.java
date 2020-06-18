package com.icheero.app.activity.ui.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.icheero.sdk.util.Log;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;

public class TouchView extends View
{
    private final Class<TouchView> TAG = TouchView.class;
    public TouchView(Context context)
    {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        Log.i(TAG, toString() + " dispatchTouchEvent" + ", action: " + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        Log.i(TAG, toString() + " onTouchEvent" + ", action: " + ev.getAction());
        return true;
    }

    @NotNull
    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
