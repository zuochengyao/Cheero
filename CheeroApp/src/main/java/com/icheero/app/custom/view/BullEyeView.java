package com.icheero.app.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zuochengyao on 2018/2/26.
 */

public class BullEyeView extends View
{
    private Paint mPaint;
    private Point mCenter;
    private float mRadius;

    private int mCenterPointColor = Color.RED;

    public int getCenterPointColor()
    {
        return mCenterPointColor;
    }

    public void setCenterPointColor(int mCenterPointColor)
    {
        this.mCenterPointColor = mCenterPointColor;
    }

    public BullEyeView(Context context)
    {
        this(context, null);
    }

    public BullEyeView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public BullEyeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mCenter = new Point();
    }

    private int getMeasurement(int measureSpec, int contentSize)
    {
        int specSize = 0;
        switch (MeasureSpec.getMode(measureSpec))
        {
            case MeasureSpec.AT_MOST:
                specSize = Math.min(measureSpec, contentSize);
                break;
            case MeasureSpec.EXACTLY:
                specSize = MeasureSpec.getSize(measureSpec);
                break;
            case MeasureSpec.UNSPECIFIED:
                specSize = contentSize;
                break;
        }
        return specSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width, height;
        int contentWidth = 100;
        int contentHeight = 100;
        width = getMeasurement(widthMeasureSpec, contentWidth);
        height = getMeasurement(heightMeasureSpec, contentHeight);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh)
        {
            mCenter.x = w / 2;
            mCenter.y = h / 2;
            mRadius = Math.min(mCenter.x, mCenter.y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius * 0.8f, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius * 0.6f, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius * 0.4f, mPaint);

        mPaint.setColor(mCenterPointColor);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius * 0.1f, mPaint);
    }
}
