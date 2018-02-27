package com.zcy.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.zcy.app.R;
import com.zcy.app.custom.view.BullEyeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends Activity
{
    @BindView(R.id.bullEye)
    BullEyeView mBullEye;
    @BindView(R.id.animate_alpha_x)
    Button mAlphaX;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setDuration(1000L);
        getWindow().setEnterTransition(slide);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.animate_alpha_x})
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
        }
    }
}
