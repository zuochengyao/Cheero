package com.zcy.app.custom.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by zuochengyao on 2018/2/28.
 */

public class PanGestureScrollView extends FrameLayout
{
    private GestureDetector mDetector;
    private Scroller mScroller;

    // 最后位移事件的位置
    private float mInitialX, mInitialY;
    // 拖拽阈值
    private int mTouchSlop;

    public PanGestureScrollView(@NonNull Context context)
    {
        this(context, null);
    }

    public PanGestureScrollView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public PanGestureScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        mDetector = new GestureDetector(context, mListener);
        mScroller = new Scroller(context);
        // 获得触摸阈值的系统常量
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private GestureDetector.SimpleOnGestureListener mListener = new GestureDetector.SimpleOnGestureListener()
    {
        @Override
        public boolean onDown(MotionEvent e)
        {
            // 取消当前的急滑动画
            if (!mScroller.isFinished())
                mScroller.abortAnimation();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            scrollBy((int) distanceX, (int) distanceY);
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            // 调用一个辅助方法来启动滚动动画
            fling((int) -velocityX / 3, (int) -velocityY / 3);
            return true;
        }
    };

    /**
     * 保证生成的子试图尽可能大
     * 默认实现会强制一些子试图和该试图一样大
     */
    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec)
    {
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
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
        }
        postInvalidate();
    }

    /**
     * 覆写进行每个滚动请求的边界检测
     */
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
                mInitialX = event.getX();
                mInitialY = event.getY();
                // 将按下时间传给手势检测器
                mDetector.onTouchEvent(event);
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                final float x = event.getX();
                final int xDiff = (int) Math.abs(x - mInitialX);
                final float y = event.getY();
                final int yDiff = (int) Math.abs(y - mInitialY);
                // 检查x或y上的距离是否适合拖拽
                if (xDiff > mTouchSlop || yDiff > mTouchSlop)
                    return true;
                break;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    /**
     * 将接受的所有触摸时间传给检测器处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mDetector.onTouchEvent(event);
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
