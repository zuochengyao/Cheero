package com.icheero.common.manager;

import android.app.Activity;

import com.icheero.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ViewManager
{
    private static Stack<Activity> activityStack;
    private static List<BaseFragment> fragmentList;

    private static volatile ViewManager mInstance;

    private ViewManager()
    {
        activityStack = new Stack<>();
        fragmentList = new ArrayList<>();
    }

    public static ViewManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ViewManager.class)
            {
                if (mInstance == null)
                    mInstance = new ViewManager();
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity)
    {
        if (activity != null)
            activityStack.add(activity);
    }

    public void removeActivity(Activity activity)
    {
        activityStack.remove(activity);
    }

    public void addFragment(int index, BaseFragment fragment)
    {
        fragmentList.add(index, fragment);
    }
}
