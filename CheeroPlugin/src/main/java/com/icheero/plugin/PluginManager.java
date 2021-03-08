package com.icheero.plugin;

import android.content.res.AssetManager;

import com.icheero.sdk.core.storage.file.FileScopeManager;

import java.io.File;
import java.lang.reflect.Method;

public class PluginManager
{
    public static File get(String pluginName)
    {
        return FileScopeManager.getInstance().getPluginFile(pluginName);
    }

    public static AssetManager getPluginAssetManager(File apk) throws Exception
    {
        Class<?> forName = Class.forName("android.content.res.AssetManager");
        Method[] declareMethods = forName.getDeclaredMethods();
        AssetManager assetManager = null;
        for (Method method : declareMethods)
        {
            if (method.getName().equals("addAssetPath"))
            {
                assetManager = AssetManager.class.newInstance();
                method.invoke(assetManager, apk.getAbsolutePath());
                break;
            }
        }
        return assetManager;
    }
}
