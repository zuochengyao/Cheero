package com.icheero.plugin;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class PluginManager
{
    private static final String PLUGIN_ASSET_PATH = "plugin/apk/";
    public static final String PLUGIN_FILE_PATH = Environment.DIRECTORY_DOWNLOADS + File.separator;

    public static void loadPlugin(Activity context, String pluginName)
    {
        File filePath = context.getExternalFilesDir(PLUGIN_FILE_PATH);
        InputStream is = null;
        FileOutputStream fos;
        int len;
        byte[] buffer = new byte[1024];
        try
        {
            is = context.getAssets().open(PLUGIN_ASSET_PATH + pluginName);
            fos = new FileOutputStream(PLUGIN_FILE_PATH + pluginName);
            while ((len = is.read(buffer)) != -1)
                fos.write(buffer, 0, len);
            Toast.makeText(context, "插件下载成功", Toast.LENGTH_SHORT).show();
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
                    is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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
