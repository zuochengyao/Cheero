package com.icheero.app.activity.ui;

import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.app.custom.view.BullEyeView;
import com.icheero.sdk.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity
{
    @BindView(R.id.bullEye)
    BullEyeView mBullEye;
    @BindView(R.id.animate_alpha_x)
    Button mAlphaX;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_custom_view);
        super.onCreate(savedInstanceState);
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setDuration(1000L);
        getWindow().setEnterTransition(slide);
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
