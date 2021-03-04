package com.icheero.plugin.activity;

import android.os.Bundle;

import com.icheero.plugin.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.RefUtils;

import dalvik.system.BaseDexClassLoader;

public class LoadMegliveActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_meglive);
    }

    private void loadClass()
    {
        try
        {
            // 获取宿主 pathList
            Object hostPathList = RefUtils.getObjectDeclaredFieldValue(BaseDexClassLoader.class, "pathList", getClassLoader());
            // 获取宿主 dexElements
            Object[] hostDexElements = (Object[]) RefUtils.getObjectDeclaredFieldValue(hostPathList.getClass(), "dexElements", hostPathList);
            // 获取插件 pathlist
//            DexClassLoader dexClassLoader = new DexClassLoader()
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}