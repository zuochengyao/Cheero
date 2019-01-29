package com.icheero.sdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.icheero.sdk.core.listener.IAppInitListener;

import java.util.ArrayList;
import java.util.List;

public class ManifestParser
{
    private static final String META_DATA_VALUE = "AppInit";
    private final Context context;

    public ManifestParser(Context context)
    {
        this.context = context;
    }

    public List<IAppInitListener> parse()
    {
        List<IAppInitListener> modules = new ArrayList<>();
        try
        {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null)
            {
                for (String key : appInfo.metaData.keySet())
                {
                    //会对其中value为IModuleConfig的meta-data进行解析，并通过反射生成实例
                    if (META_DATA_VALUE.equals(appInfo.metaData.get(key)))
                        modules.add(parseModule(key));
                }
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException("Unable to find metadata to parse IModuleConfig", e);
        }
        return modules;
    }

    //通过类名生成实例
    private static IAppInitListener parseModule(String className)
    {
        Class<?> clazz;
        try
        {
            clazz = Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Unable to find IModuleConfig implementation", e);
        }
        Object module;
        try
        {
            module = clazz.newInstance();
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException("Unable to instantiate IModuleConfig implementation for " + clazz, e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Unable to instantiate IModuleConfig implementation for " + clazz, e);
        }

        if (!(module instanceof IAppInitListener))
        {
            throw new RuntimeException("Expected instanceof IModuleConfig, but found: " + module);
        }
        return (IAppInitListener) module;
    }
}
