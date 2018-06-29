package com.icheero.app.activity.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.icheero.app.R;
import com.icheero.sdk.control.plugin.PluginManager;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dalvik.system.DexClassLoader;

public class LoadPluginActivity extends Activity
{
    private static final Class TAG = LoadPluginActivity.class;

    @BindView(R.id.image_volume_bigger)
    ImageView ivVolumeBigger;
    @BindView(R.id.image_volume_smaller)
    ImageView ivVolumeSmaller;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_plugin);
        // 动态申请存储权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) requestPermissions(perms, 200);
        }
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_volume_bigger, R.id.image_volume_smaller})
    public void OnClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.image_volume_bigger:
                handleAnim(v);
                break;
            case R.id.image_volume_smaller:
                Drawable background = v.getBackground();
                if (background != null && background instanceof AnimationDrawable)
                {
                    handleAnim(v);
                    return;
                }
                File apk = new File(PluginManager.PLUGIN_FILE_PATH);
                // 检查本地是否有插件apk
                if (apk.exists())
                {
                    // 加载到内存
                    DexClassLoader classLoader = new DexClassLoader(apk.getAbsolutePath(), this.getDir(PluginManager.PLUGIN_NAME, Context.MODE_PRIVATE).getAbsolutePath(), null, getClassLoader());
                    // 获取本地资源
                    // Drawable drawable = this.getResources().getDrawable(R.drawable.volume_bigger);
                    try
                    {
                        // 获取插件资源 - 利用反射
                        Class<?> loadClass = classLoader.loadClass(PluginManager.PLUGIN_PACKAGE_NAME + ".R$drawable");
                        Field[] declareField = loadClass.getDeclaredFields();
                        for (Field field : declareField)
                        {
                            if (field.getName().equals("volume_smaller"))
                            {
                                int animId = field.getInt(R.drawable.class);
                                AssetManager assetManager = PluginManager.getPluginAssetManager(apk);
                                if (assetManager != null)
                                {
                                    Resources resources = new Resources(assetManager, super.getResources().getDisplayMetrics(), super.getResources().getConfiguration());
                                    Drawable drawable = resources.getDrawable(animId);
                                    v.setBackground(drawable);
                                    handleAnim(v);
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else PluginManager.loadPlugin(this); // 加载插件
                break;
        }
    }

    private void handleAnim(View v)
    {
        AnimationDrawable animationDrawable = (AnimationDrawable) v.getBackground();
        if (animationDrawable != null)
        {
            if (animationDrawable.isRunning()) animationDrawable.stop();
            else animationDrawable.start();
        }
    }
}
