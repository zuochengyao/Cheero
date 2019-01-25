package com.icheero.app.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.icheero.app.R;
import com.icheero.app.activity.data.CustomSettingActivity;
import com.icheero.app.activity.data.DatabaseActivity;
import com.icheero.app.activity.data.SystemSettingActivity;
import com.icheero.app.activity.data.ViewModelActivity;
import com.icheero.app.activity.media.CameraActivity;
import com.icheero.app.activity.network.DownloadActivity;
import com.icheero.app.activity.network.ImageDownloadActivity;
import com.icheero.app.activity.network.RequestActivity;
import com.icheero.app.activity.network.RetrofitActivity;
import com.icheero.app.activity.network.WebViewActivity;
import com.icheero.app.activity.plugin.LoadPluginActivity;
import com.icheero.app.activity.ui.AnimActivity;
import com.icheero.app.activity.ui.CustomViewActivity;
import com.icheero.app.activity.ui.DialogActivity;
import com.icheero.app.activity.ui.OptionActivity;
import com.icheero.app.activity.ui.SectionsActivity;
import com.icheero.app.activity.ui.StyledActivity;
import com.icheero.app.activity.ui.touch.PanGestureScrollActivity;
import com.icheero.app.activity.ui.touch.PanScrollActivity;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.util.Common;
import com.icheero.util.IOManager;
import com.icheero.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
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
    @BindView(R.id.to_retrofit_activity)
    Button toRetrofitActivity;
    @BindView(R.id.to_request_activity)
    Button toRequestActivity;
    @BindView(R.id.to_camera_activity)
    Button toCameraActivity;
    @BindView(R.id.to_custom_setting_activity)
    Button toCustomSettingActivity;
    @BindView(R.id.to_system_setting_activity)
    Button toSystemSettingActivity;
    @BindView(R.id.to_database_activity)
    Button toDatabaseActivity;
    @BindView(R.id.to_view_model_activity)
    Button toViewModelActivity;
    @BindView(R.id.to_load_plugin_activity)
    Button toLoadPluginActivity;
    @BindView(R.id.to_faceid_activity)
    Button toFaceIDActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
        Slide slide = new Slide();
        slide.setDuration(700);
        getWindow().setExitTransition(slide);
        ButterKnife.bind(this);
        if (!mPermissionManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            mPermissionManager.permissionRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        else
            IOManager.getInstance().createRootFolder();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @OnClick({R.id.to_custom_setting_activity, R.id.to_system_setting_activity, R.id.to_database_activity, R.id.to_view_model_activity})
    public void OnDataClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_custom_setting_activity:
            {
                startActivity(new Intent(this, CustomSettingActivity.class));
                break;
            }
            case R.id.to_system_setting_activity:
            {
                startActivity(new Intent(this, SystemSettingActivity.class));
                break;
            }
            case R.id.to_database_activity:
            {
                startActivity(new Intent(this, DatabaseActivity.class));
                break;
            }
            case R.id.to_view_model_activity:
            {
                startActivity(new Intent(this, ViewModelActivity.class));
                break;
            }
        }
    }

    @OnClick({R.id.to_camera_activity})
    public void OnMediaClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_camera_activity:
            {
                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_REQUEST_CODE, 1);
                startActivity(intent);
                break;
            }
        }
    }

    @OnClick({R.id.to_download_activity, R.id.to_image_download_activity, R.id.to_web_view_activity, R.id.to_retrofit_activity, R.id.to_request_activity})
    public void OnNetworkClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_web_view_activity:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case R.id.to_image_download_activity:
                startActivity(new Intent(this, ImageDownloadActivity.class));
                break;
            case R.id.to_download_activity:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.to_retrofit_activity:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.to_request_activity:
                startActivity(new Intent(this, RequestActivity.class));
                break;
        }
    }

    @OnClick({R.id.to_styled_activity, R.id.to_custom_view_activity, R.id.to_sections_activity, R.id.to_anim_activity, R.id.to_dialog_activity, R.id.to_option_activity, R.id.to_touch_pan_scroll_activity, R.id.to_touch_pan_gesture_scroll_activity})
    public void OnUIClickEvent(View v)
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
        }
    }

    @OnClick({R.id.to_load_plugin_activity})
    public void OnPluginClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_load_plugin_activity:
                startActivity(new Intent(this, LoadPluginActivity.class));
                break;
        }
    }

    @OnClick({R.id.to_faceid_activity})
    public void OnModuleClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_faceid_activity:
                // startActivity(new Intent(this, FaceIDActivity.class));
                // ARouter.getInstance().build("/faceid/index").navigation();
                Log.e(MainActivity.class, "test");
                break;
        }
    }

    @Override
    public void onPermissionRequest(boolean isGranted, String permission)
    {
        super.onPermissionRequest(isGranted, permission);
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            if (isGranted)
                IOManager.getInstance().createRootFolder();
            else
                Common.toast(this, "请打开读写权限！", Toast.LENGTH_SHORT);
        }
    }
}
