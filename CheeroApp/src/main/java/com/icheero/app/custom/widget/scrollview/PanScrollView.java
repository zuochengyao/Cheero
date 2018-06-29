package com.icheero.app.custom.widget.scrollview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by zuochengyao on 2018/2/28.
 */

public class PanScrollView extends FrameLayout
{
    // 急滑控件
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    /* 上一个移动事件位置 */
    private float mLastTouchX, mLastTouchY;
    /* 拖动阈值 */
    private int mTouchSlop;
    /* 急滑速度 */
    private int mMaxVelocity, mMinVelocity;
    /* 拖动锁 */
    private boolean mDragging = false;

    public PanScrollView(@NonNull Context context)
    {
        this(context, null);
    }

    public PanScrollView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public PanScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        // 获得触摸阈值的系统常量
        mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    /**
     * 保证子试图尽可能大
     * 默认实现会强制一些子试图和该试图一样大
     */
    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec)
    {
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed)
    {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.leftMargin + lp.rightMargin, MeasureSpec.UNSPECIFIED);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(lp.topMargin + lp.bottomMargin, MeasureSpec.UNSPECIFIED);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            // 在ViewGroup绘制时调用
            // 保证急滑动画顺利完成
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            if (getChildCount() > 0)
            {
                View child = getChildAt(0);
                x = clamp(x, getWidth() - getPaddingRight() - getPaddingLeft(), child.getWidth());
                y = clamp(y, getHeight() - getPaddingBottom() - getPaddingTop(), child.getHeight());
                if (x != oldX || y != oldY)
                    scrollTo(x, y);
            }
            postInvalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (getChildCount() > 0)
        {
            View child = getChildAt(0);
            x = clamp(x, getWidth() - getPaddingRight() - getPaddingLeft(), child.getWidth());
            y = clamp(y, getHeight() - getPaddingBottom() - getPaddingTop(), child.getHeight());
            if (x != getScrollX() || y != getScrollY())
                super.scrollTo(x, y);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                // 还原速度跟踪器
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                // 保存初始出点
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                final float x = event.getX();
                final int xDiff = (int) Math.abs(x - mLastTouchX);
                final float y = event.getY();
                final int yDiff = (int) Math.abs(y - mLastTouchY);
                // 检查x或y上的距离是否适合拖拽
                if (xDiff > mTouchSlop || yDiff > mTouchSlop)
                {
                    mDragging = true;
                    mVelocityTracker.addMovement(event);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            {
                mDragging = false;
                mVelocityTracker.clear();
                break;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mVelocityTracker.addMovement(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
            {
                final float x = event.getX();
                float deltaX = mLastTouchX - x;
                final float y = event.getY();
                float deltaY = mLastTouchY - y;
                // 检查各个方向的事件上的阈值
                if (((Math.abs(deltaX)) > mTouchSlop || (Math.abs(deltaY)) > mTouchSlop) && !mDragging)
                    mDragging = true;
                if (mDragging)
                {
                    scrollBy((int) deltaX, (int) deltaY);
                    mLastTouchX = x;
                    mLastTouchY = y;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            {
                mDragging = false;
                // 终止所以进行的急滑动画
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                mDragging = false;
                // 计算当前的速度，如果高于最小阈值，则启动一个急滑
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                int velocityX = (int) mVelocityTracker.getXVelocity();
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityX) > mMinVelocity || Math.abs(velocityY) > mMinVelocity)
                    fling(-velocityX, -velocityY);
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 初始化Scroller和开始重新绘制的使用方法
     */
    public void fling(int velocityX, int velocityY)
    {
        if (getChildCount() > 0)
        {
            int height = getHeight() - getPaddingBottom() - getPaddingTop();
            int width = getWidth() - getPaddingLeft() - getPaddingRight();
            int bottom = getChildAt(0).getHeight();
            int right = getChildAt(0).getWidth();
            mScroller.fling(getScrollX(), getScrollY(), velocityX, velocityY, 0, Math.max(0, right - width), 0, Math.max(0, bottom - height));
            invalidate();
        }
    }

    private int clamp(int n, int my, int child)
    {
        if (my >= child || n < 0)
            return 0;
        if ((my + n) > child)
            return child - my;
        return n;
    }
}
