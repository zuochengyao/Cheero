package com.icheero.sdk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager
{
    private boolean isPageEnabled = true;

    public NoScrollViewPager(@NonNull Context context)
    {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return isPageEnabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return isPageEnabled && super.onInterceptTouchEvent(ev);
    }

    /**
     * 设置Pager是否可滑动
     */
    public void setPageEnabled(boolean enabled)
    {
        this.isPageEnabled = enabled;
    }
}
