package com.icheero.plugin.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.icheero.plugin.R;
import com.icheero.plugin.framework.PluginManager;
import com.icheero.sdk.base.BaseActivity;

import java.io.File;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class LoadPluginActivity extends BaseActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_plugin);
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED)
            requestPermissions(perms, 200);
        doInitView();
    }
    
    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.image_volume_bigger)
            handleAnim(v);
        else if (i == R.id.image_volume_smaller)
        {
            Drawable background = v.getBackground();
            if (background instanceof AnimationDrawable)
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
            else
                PluginManager.loadPlugin(this); // 加载插件
        }
    }
    
    private void doInitView()
    {
        ImageView ivVolumeBigger = findViewById(R.id.image_volume_bigger);
        ivVolumeBigger.setOnClickListener(this);
        ImageView ivVolumeSmaller = findViewById(R.id.image_volume_smaller);
        ivVolumeSmaller.setOnClickListener(this);
    }

    private void handleAnim(View v)
    {
        AnimationDrawable animationDrawable = (AnimationDrawable) v.getBackground();
        if (animationDrawable != null)
        {
            if (animationDrawable.isRunning())
                animationDrawable.stop();
            else
                animationDrawable.start();
        }
    }
}
