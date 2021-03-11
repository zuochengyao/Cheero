package com.icheero.plugin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.icheero.plugin.PluginManager;
import com.icheero.plugin.R;
import com.icheero.plugin.hook.GlobalActivityHookHelper;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.RefUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

@Route(path = "/plugin/main")
public class MainActivity extends BaseActivity implements View.OnClickListener
{
    public static final String PLUGIN_ASSET_PATH = "plugin/apk/";
    public static final String PLUGIN_NAME_MEGLIVE = "meglive.apk";
    public static final String PLUGIN_PACKAGE_MEGLIVE = "com.megvii.meglive_still.demo";
    public static final String PLUGIN_NAME_CHEERO = "CheeroPlugin.apk";
    public static final String PLUGIN_PACKAGE_CHEERO = "com.icheero.plugins";

    private Button mToCustom;
    private Button mToMeglive;
    private Button mToTinker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        doInitView();
        try
        {
            GlobalActivityHookHelper.hook();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void doInitView()
    {
        mToCustom = findViewById(R.id.to_custom);
        mToTinker = findViewById(R.id.to_tinker);
        mToMeglive = findViewById(R.id.to_meglive);

        mToCustom.setOnClickListener(this);
        mToTinker.setOnClickListener(this);
        mToMeglive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        Intent intent = null;
        if (id == R.id.to_custom)
        {
            downloadApk(PLUGIN_NAME_CHEERO);
            intent = new Intent(this, LoadPluginActivity.class);
        }
        else if (id == R.id.to_meglive)
        {
            downloadApk(PLUGIN_NAME_MEGLIVE);
            loadClass();
            try
            {
                intent = new Intent(this,
                        RefUtils.getClass(PLUGIN_PACKAGE_MEGLIVE + ".MainActivity"));
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        else if (id == R.id.to_tinker)
        {
            intent = new Intent(this, TinkerActivity.class);
        }
        startActivity(intent);
    }

    private void downloadApk(String filename)
    {
        File apk = PluginManager.get(filename);
        // 检查本地是否有插件apk
        if (!apk.exists())
        {
            // 模拟下载插件
            InputStream is = null;
            FileOutputStream fos;
            int len;
            byte[] buffer = new byte[1024];
            try
            {
                is = getAssets().open(PLUGIN_ASSET_PATH + filename);
                fos = new FileOutputStream(apk);
                while ((len = is.read(buffer)) != -1) fos.write(buffer, 0, len);
                Toast.makeText(this, "apk download success", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (is != null)
                    {
                        is.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadClass()
    {
        try
        {
            // 获取 宿主 pathList
            Object hostPathList = RefUtils.getObjectDeclaredFieldValue(BaseDexClassLoader.class,
                    "pathList", getClassLoader());
            Field hostDexElementsField = RefUtils.getDeclaredField(hostPathList.getClass(),
                    "dexElements");
            // 获取 宿主 dexElements
            Object[] hostDexElements = (Object[]) hostDexElementsField.get(hostPathList);

            File apk = PluginManager.get(MainActivity.PLUGIN_NAME_MEGLIVE);
            // 获取 插件 pathList
            DexClassLoader dexClassLoader = new DexClassLoader(apk.getAbsolutePath(),
                    getCacheDir().getAbsolutePath(), null, getClassLoader());
            Object pluginPathList = RefUtils.getObjectDeclaredFieldValue(BaseDexClassLoader.class,
                    "pathList", dexClassLoader);
            // 获取 插件 dexElements
            Object[] pluginDexElements = (Object[]) RefUtils.getObjectDeclaredFieldValue(
                    pluginPathList.getClass(), "dexElements", pluginPathList);

            // 创建新的 dexElements
            Object[] newDexElements = (Object[]) Array.newInstance(
                    hostDexElements.getClass().getComponentType(),
                    hostDexElements.length + pluginDexElements.length);
            System.arraycopy(hostDexElements, 0, newDexElements, 0, hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, newDexElements, hostDexElements.length,
                    pluginDexElements.length);

            // 将新的 dexElements 赋值给 dexElements
            hostDexElementsField.set(hostPathList, newDexElements);
            PluginManager.mergePluginResource(getBaseContext(), apk);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
