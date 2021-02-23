package com.icheero.app.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.icheero.app.R;
import com.icheero.app.activity.data.CustomSettingActivity;
import com.icheero.app.activity.data.DatabaseActivity;
import com.icheero.app.activity.data.SystemSettingActivity;
import com.icheero.app.activity.data.ViewModelActivity;
import com.icheero.app.activity.feature.LollipopActivity;
import com.icheero.app.activity.feature.oreo.NotificationActivity;
import com.icheero.app.activity.framework.RxJavaActivity;
import com.icheero.app.activity.framework.eventbus.EventBusActivity;
import com.icheero.app.activity.framework.xposed.XposedActivity;
import com.icheero.app.activity.media.camera.GLSurfaceViewActivity;
import com.icheero.app.activity.media.camera.SurfaceViewActivity;
import com.icheero.app.activity.media.camera.SystemCameraActivity;
import com.icheero.app.activity.media.camera.TextureViewActivity;
import com.icheero.app.activity.media.video.AudioExtractorActivity;
import com.icheero.app.activity.memory.WeakHandlerActivity;
import com.icheero.app.activity.network.DownloadActivity;
import com.icheero.app.activity.network.ImageDownloadActivity;
import com.icheero.app.activity.network.RequestActivity;
import com.icheero.app.activity.network.RetrofitActivity;
import com.icheero.app.activity.network.WebViewActivity;
import com.icheero.app.activity.plugin.PluginActivity;
import com.icheero.app.activity.reverse.DisposeDexActivity;
import com.icheero.app.activity.reverse.DisposeManifestActivity;
import com.icheero.app.activity.reverse.DisposeResourceActivity;
import com.icheero.app.activity.reverse.DisposeSoActivity;
import com.icheero.app.activity.reverse.JniActivity;
import com.icheero.app.activity.service.AboutServiceActivity;
import com.icheero.app.activity.service.UserAidlClientActivity;
import com.icheero.app.activity.thread.AsyncTaskActivity;
import com.icheero.app.activity.ui.AnimActivity;
import com.icheero.app.activity.ui.CustomViewActivity;
import com.icheero.app.activity.ui.DialogActivity;
import com.icheero.app.activity.ui.MoveViewActivity;
import com.icheero.app.activity.ui.OptionActivity;
import com.icheero.app.activity.ui.SectionsActivity;
import com.icheero.app.activity.ui.StyledActivity;
import com.icheero.app.activity.ui.touch.PanGestureScrollActivity;
import com.icheero.app.activity.ui.touch.PanScrollActivity;
import com.icheero.app.activity.ui.touch.TouchEventActivity;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.CameraManager;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.util.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
{
    // region ButterKnife Views
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
    @BindView(R.id.to_touch_event_activity)
    Button toTouchEventActivity;
    @BindView(R.id.to_move_view_activity)
    Button toMoveViewActivity;
    @BindView(R.id.to_notification_activity)
    Button toNotificationActivity;
    @BindView(R.id.to_lollipop_activity)
    Button toTabLayoutActivity;
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
    @BindView(R.id.to_surface_view_activity)
    Button toSurfaceViewActivity;
    @BindView(R.id.to_texture_view_activity)
    Button toTextureViewActivity;
    @BindView(R.id.to_glsurface_view_activity)
    Button toGLSurfaceViewActivity;
    @BindView(R.id.to_audio_extractor_activity)
    Button toAudioExtractorActivity;
    @BindView(R.id.to_custom_setting_activity)
    Button toCustomSettingActivity;
    @BindView(R.id.to_system_setting_activity)
    Button toSystemSettingActivity;
    @BindView(R.id.to_database_activity)
    Button toDatabaseActivity;
    @BindView(R.id.to_view_model_activity)
    Button toViewModelActivity;
    @BindView(R.id.to_jni_activity)
    Button toJniActivity;
    @BindView(R.id.to_dispose_so_activity)
    Button toDisposeSoActivity;
    @BindView(R.id.to_dispose_manifest_activity)
    Button toDisposeManifestActivity;
    @BindView(R.id.to_dispose_resource_activity)
    Button toDisposeResourceActivity;
    @BindView(R.id.to_dispose_dex_activity)
    Button toDisposeDexActivity;
    @BindView(R.id.to_load_plugin_activity)
    Button toLoadPluginActivity;
    @BindView(R.id.to_xposed_activity)
    Button toXposedActivity;
    @BindView(R.id.to_rx_java_activity)
    Button toRxJavaActivity;
    @BindView(R.id.to_event_bus_activity)
    Button toEventBusActivity;
    @BindView(R.id.to_flutter_activity)
    Button toFlutterActivity;
    @BindView(R.id.to_user_aidl_client_activity)
    Button toUserAidlClientActivity;
    @BindView(R.id.to_about_service_activity)
    Button toAboutServiceActivity;
    @BindView(R.id.to_async_task_activity)
    Button toAsyncTaskActivity;
    @BindView(R.id.to_weak_handler_activity)
    Button toWeakHandlerActivity;
    @BindView(R.id.to_plugin_activity)
    Button toPluginActivity;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                openActivity(CustomSettingActivity.class);
                break;
            }
            case R.id.to_system_setting_activity:
            {
                openActivity(SystemSettingActivity.class);
                break;
            }
            case R.id.to_database_activity:
            {
                openActivity(DatabaseActivity.class);
                break;
            }
            case R.id.to_view_model_activity:
            {
                openActivity(ViewModelActivity.class);
                break;
            }
        }
    }

    @OnClick({R.id.to_audio_extractor_activity, R.id.to_glsurface_view_activity, R.id.to_texture_view_activity, R.id.to_surface_view_activity, R.id.to_camera_activity})
    public void OnMediaClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_camera_activity:
            {
                Bundle bundle = new Bundle();
                bundle.putInt(SystemCameraActivity.KEY_REQUEST_CODE, CameraManager.REQUEST_CODE_IMAGE);
                openActivity(SystemCameraActivity.class, bundle);
                break;
            }
            case R.id.to_surface_view_activity:
            {
                openActivity(SurfaceViewActivity.class);
                break;
            }
            case R.id.to_texture_view_activity:
            {
                openActivity(TextureViewActivity.class);
                break;
            }
            case R.id.to_glsurface_view_activity:
                openActivity(GLSurfaceViewActivity.class);
                break;
            case R.id.to_audio_extractor_activity:
                openActivity(AudioExtractorActivity.class);
                break;
        }
    }

    @OnClick({R.id.to_download_activity, R.id.to_image_download_activity, R.id.to_web_view_activity, R.id.to_retrofit_activity, R.id.to_request_activity})
    public void OnNetworkClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_web_view_activity:
                openActivity(WebViewActivity.class);
                break;
            case R.id.to_image_download_activity:
                openActivity(ImageDownloadActivity.class);
                break;
            case R.id.to_download_activity:
                openActivity(DownloadActivity.class);
                break;
            case R.id.to_retrofit_activity:
                openActivity(RetrofitActivity.class);
                break;
            case R.id.to_request_activity:
                openActivity(RequestActivity.class);
                break;
        }
    }

    @OnClick({
            R.id.to_move_view_activity, R.id.to_styled_activity, R.id.to_custom_view_activity, R.id.to_sections_activity, R.id.to_anim_activity, R.id.to_dialog_activity, R.id.to_option_activity,
            R.id.to_touch_pan_scroll_activity, R.id.to_touch_pan_gesture_scroll_activity, R.id.to_touch_event_activity
    })
    public void OnUIClickEvent(View v)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {

            switch (v.getId())
            {

            }
        }
        switch (v.getId())
        {
            case R.id.to_styled_activity:
            {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                bundle.putString("transition", "explode");
                openActivity(StyledActivity.class, bundle);
                break;
            }
            case R.id.to_custom_view_activity:
            {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                bundle.putString("transition", "slide");
                openActivity(CustomViewActivity.class, bundle);
                break;
            }
            case R.id.to_sections_activity:
            {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                bundle.putString("transition", "fade");
                openActivity(SectionsActivity.class, bundle);
                break;
            }
            case R.id.to_anim_activity:
            {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                bundle.putString("transition", "fade");
                openActivity(AnimActivity.class, bundle);
                break;
            }
            case R.id.to_move_view_activity:
            {
                openActivity(MoveViewActivity.class);
                break;
            }
            case R.id.to_dialog_activity:
            {
                openActivity(DialogActivity.class);
                break;
            }
            case R.id.to_option_activity:
            {
                openActivity(OptionActivity.class);
                break;
            }
            case R.id.to_touch_pan_gesture_scroll_activity:
            {
                openActivity(PanGestureScrollActivity.class);
                break;
            }
            case R.id.to_touch_pan_scroll_activity:
            {
                openActivity(PanScrollActivity.class);
                break;
            }
            case R.id.to_touch_event_activity:
            {
                openActivity(TouchEventActivity.class);
                break;
            }
        }
    }

    @OnClick({R.id.to_lollipop_activity, R.id.to_notification_activity})
    public void OnFeatureClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_notification_activity:
            {
                openActivity(NotificationActivity.class);
                break;
            }
            case R.id.to_lollipop_activity:
            {
                openActivity(LollipopActivity.class);
                break;
            }
        }
    }

    @OnClick({R.id.to_jni_activity, R.id.to_dispose_so_activity, R.id.to_dispose_manifest_activity, R.id.to_dispose_resource_activity, R.id.to_dispose_dex_activity})
    public void OnReverseClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_jni_activity:
                openActivity(JniActivity.class);
                break;
            case R.id.to_dispose_so_activity:
                openActivity(DisposeSoActivity.class);
                break;
            case R.id.to_dispose_manifest_activity:
                openActivity(DisposeManifestActivity.class);
                break;
            case R.id.to_dispose_resource_activity:
                openActivity(DisposeResourceActivity.class);
                break;
            case R.id.to_dispose_dex_activity:
                openActivity(DisposeDexActivity.class);
                break;
        }
    }

    @OnClick({R.id.to_load_plugin_activity})
    public void OnModuleClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_load_plugin_activity:
            {
                ARouter.getInstance().build("/plugin/index").navigation();
                break;
            }
        }
    }

    @OnClick({R.id.to_plugin_activity, R.id.to_rx_java_activity, R.id.to_event_bus_activity, R.id.to_xposed_activity, R.id.to_flutter_activity})
    public void OnFrameworkClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_rx_java_activity:
            {
                openActivity(RxJavaActivity.class);
                break;
            }
            case R.id.to_event_bus_activity:
            {
                openActivity(EventBusActivity.class);
                break;
            }
            case R.id.to_xposed_activity:
            {
                openActivity(XposedActivity.class);
                break;
            }
            case R.id.to_plugin_activity:
            {
                openActivity(PluginActivity.class);
                break;
            }
        }
    }

    @OnClick({R.id.to_user_aidl_client_activity, R.id.to_about_service_activity})
    public void OnServiceClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_user_aidl_client_activity:
                openActivity(UserAidlClientActivity.class);
                break;
            case R.id.to_about_service_activity:
                openActivity(AboutServiceActivity.class);
                break;
        }
    }

    @OnClick({R.id.to_async_task_activity})
    public void OnThreadClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_async_task_activity:
                openActivity(AsyncTaskActivity.class);
                break;
        }
    }

    @OnClick(R.id.to_weak_handler_activity)
    public void onMemoryClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_weak_handler_activity:
                openActivity(WeakHandlerActivity.class);
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
