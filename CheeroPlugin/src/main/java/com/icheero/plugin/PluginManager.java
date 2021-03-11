package com.icheero.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.icheero.sdk.core.storage.file.FileScopeManager;

import java.io.File;
import java.lang.reflect.Field;
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

    public static void mergePluginResource(Context application, File apk) throws Exception
    {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
        // 塞入原来宿主的资源
        addAssetPath.invoke(assetManager, application.getPackageResourcePath());
        // 塞入插件的资源
        addAssetPath.invoke(assetManager, apk.getAbsolutePath());

        // 创建一个新的 Resources 对象
        Resources newResourcesObj = new Resources(assetManager,
                application.getResources().getDisplayMetrics(),
                application.getResources().getConfiguration());

        // 获取 ContextImpl 中的 Resources 类型的 mResources 变量，并替换它的值为新的 Resources 对象
        Field resourcesField = application.getClass().getDeclaredField("mResources");
        resourcesField.setAccessible(true);
        resourcesField.set(application, newResourcesObj);

        // ----------------------------------------------
//
//        // 获取 ContextImpl 中的 LoadedApk 类型的 mPackageInfo 变量
//        Field packageInfoField = application.getClass().getDeclaredField("mPackageInfo");
//        packageInfoField.setAccessible(true);
//        Object packageInfoObj = packageInfoField.get(application);

        // 获取 mPackageInfo 变量对象中类的 Resources 类型的 mResources 变量，，并替换它的值为新的 Resources 对象
        // 注意：这是最主要的需要替换的，如果不需要支持插件运行时更新，只留这一个就可以了
//        Field resourcesField2 = packageInfoObj.getClass().getDeclaredField("mResources");
//        resourcesField2.setAccessible(true);
//        resourcesField2.set(packageInfoObj, newResourcesObj);

        // ----------------------------------------------

        // 获取 ContextImpl 中的 Resources.Theme 类型的 mTheme 变量，并至空它
        // 注意：清理mTheme对象，否则通过inflate方式加载资源会报错, 如果是activity动态加载插件，则需要把activity的mTheme对象也设置为null
//        Field themeField = application.getClass().getDeclaredField("mTheme");
//        themeField.setAccessible(true);
//        themeField.set(application, null);
    }
}
