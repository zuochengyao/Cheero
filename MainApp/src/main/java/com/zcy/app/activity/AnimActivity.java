package com.zcy.app.activity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zcy.app.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimActivity extends BaseActivity
{
    @BindView(R.id.launcher_image)
    ImageView mLauncher;
    @BindView(R.id.start_animation)
    Button mStartAnimation;
    @BindView(R.id.start_animator)
    Button mStartAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_animator);
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.launcher_image, R.id.start_animation, R.id.start_animator})
    public void OnClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.launcher_image:
            {
                Toast.makeText(this, "launcher image toast!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.start_animation:
            {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.delta_x);
                mLauncher.startAnimation(animation);
                break;
            }
            case R.id.start_animator:
            {
                mLauncher.clearAnimation();
                ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.translation_x);
                animator.start();
                break;
            }
        }
    }
}
