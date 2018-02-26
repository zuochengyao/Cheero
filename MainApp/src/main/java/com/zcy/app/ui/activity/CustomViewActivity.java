package com.zcy.app.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        ButterKnife.bind(this);
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
                final ObjectAnimator mObjectAnimator = ObjectAnimator.ofInt(mBullEye, "centerPointColor", Color.RED, Color.GREEN);
                mObjectAnimator.setDuration(3000);
                mObjectAnimator.setRepeatCount(Animation.INFINITE);
                mObjectAnimator.setRepeatMode(ValueAnimator.REVERSE);
                mObjectAnimator.start();
                break;
            }
        }
    }
}
