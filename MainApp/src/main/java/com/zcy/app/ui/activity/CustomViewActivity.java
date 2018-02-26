package com.zcy.app.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zcy.app.R;
import com.zcy.app.ui.view.BullEyeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends Activity
{
    @BindView(R.id.bullEye)
    BullEyeView mBullEye;
    @BindView(R.id.animate_alpha_x)
    Button mAlphaX;
    @BindView(R.id.animate_flipper)
    Button mFlipper;

    private ObjectAnimator mFlipperAnimate;
    private boolean isHeads = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        ButterKnife.bind(this);

        mFlipperAnimate = ObjectAnimator.ofFloat(mBullEye, "rotationX", 0f, 180f);
        mFlipperAnimate.setDuration(500);
        mFlipperAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                if (animation.getAnimatedFraction() >= 0.25f && isHeads)
                {
                    mBullEye.updateColor(Color.GREEN);
                    isHeads = false;
                }
                else if (animation.getAnimatedFraction() >= 0.75f && isHeads)
                {
                    mBullEye.updateColor(Color.RED);
                    isHeads = true;
                }
            }
        });
    }

    @OnClick({R.id.animate_alpha_x, R.id.animate_flipper})
    public void OnClickEvent(View v)
    {
        switch (v.getId())
        {
            // 透明渐变 + 水平移动
            case R.id.animate_alpha_x:
            {
                if (mBullEye.getAlpha() > 0)
                    mBullEye.animate().alpha(0f).translationX(1000f);
                else
                {
                    mBullEye.setTranslationX(10f);
                    mBullEye.animate().alpha(1f);
                }
                break;
            }
            case R.id.animate_flipper:
            {
                mFlipperAnimate.start();
                break;
            }
        }
    }
}
