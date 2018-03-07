package com.zcy.app.activity.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;

import com.zcy.app.R;
import com.zcy.app.activity.media.CameraActivity;
import com.zcy.app.activity.network.DownloadActivity;
import com.zcy.app.activity.network.ImageDownloadActivity;
import com.zcy.app.activity.network.WebViewActivity;
import com.zcy.app.activity.ui.AnimActivity;
import com.zcy.app.activity.ui.CustomViewActivity;
import com.zcy.app.activity.ui.DialogActivity;
import com.zcy.app.activity.ui.OptionActivity;
import com.zcy.app.activity.ui.SectionsActivity;
import com.zcy.app.activity.ui.StyledActivity;
import com.zcy.app.activity.ui.touch.PanGestureScrollActivity;
import com.zcy.app.activity.ui.touch.PanScrollActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.to_styled_activity)
    Button toStyledActivity;
    @BindView(R.id.to_custom_view_activity)
    Button toCustomViewActivity;
    @BindView(R.id.to_sections_activity)
    Button toSectionsActivity;
    @BindView(R.id.to_anim_activity)
    Button toAnimActivity;
    @BindView(R.id.to_dialog_activity)
    Button toDialogActivity;
    @BindView(R.id.to_option_activity)
    Button toOptionActivity;
    @BindView(R.id.to_touch_pan_gesture_scroll_activity)
    Button toPanGestureScrollActivity;
    @BindView(R.id.to_touch_pan_scroll_activity)
    Button toPanScrollActivity;
    @BindView(R.id.to_web_view_activity)
    Button toWebViewActivity;
    @BindView(R.id.to_image_download_activity)
    Button toImageDownloadActivity;
    @BindView(R.id.to_download_activity)
    Button toDownloadActivity;
    @BindView(R.id.to_camera_activity)
    Button toCameraActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        Slide slide = new Slide();
        slide.setDuration(700);
        getWindow().setExitTransition(slide);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.to_camera_activity, R.id.to_download_activity, R.id.to_image_download_activity, R.id.to_web_view_activity, R.id.to_styled_activity, R.id.to_custom_view_activity, R.id.to_sections_activity, R.id.to_anim_activity, R.id.to_dialog_activity, R.id.to_option_activity, R.id.to_touch_pan_scroll_activity, R.id.to_touch_pan_gesture_scroll_activity})
    public void OnClickEvent(View v)
    {
        Intent toActivity = new Intent();
        switch (v.getId())
        {
            case R.id.to_styled_activity:
            {
                toActivity.setClass(this, StyledActivity.class);
                toActivity.putExtra("transition", "explode");
                startActivity(toActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.to_custom_view_activity:
            {
                toActivity.setClass(this, CustomViewActivity.class);
                toActivity.putExtra("transition", "slide");
                startActivity(toActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.to_sections_activity:
            {
                toActivity.setClass(this, SectionsActivity.class);
                toActivity.putExtra("transition", "fade");
                startActivity(toActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.to_anim_activity:
            {
                toActivity.setClass(this, AnimActivity.class);
                toActivity.putExtra("transition", "fade");
                startActivity(toActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.to_dialog_activity:
            {
                startActivity(new Intent(this, DialogActivity.class));
                break;
            }
            case R.id.to_option_activity:
            {
                startActivity(new Intent(this, OptionActivity.class));
                break;
            }
            case R.id.to_touch_pan_gesture_scroll_activity:
            {
                startActivity(new Intent(this, PanGestureScrollActivity.class));
                break;
            }
            case R.id.to_touch_pan_scroll_activity:
            {
                startActivity(new Intent(this, PanScrollActivity.class));
                break;
            }
            case R.id.to_web_view_activity:
            {
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            }
            case R.id.to_image_download_activity:
            {
                startActivity(new Intent(this, ImageDownloadActivity.class));
                break;
            }
            case R.id.to_download_activity:
            {
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            }
            case R.id.to_camera_activity:
            {
                startActivity(new Intent(this, CameraActivity.class));
                break;
            }
        }
    }

}
