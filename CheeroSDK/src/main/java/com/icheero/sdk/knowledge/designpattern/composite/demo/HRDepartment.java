package com.icheero.sdk.knowledge.designpattern.composite.demo;

import com.icheero.util.Log;

/**
 * Created by zuochengyao on 2018/3/19.
 */

public class HRDepartment extends Company
{
    private static final Class<HRDepartment> TAG = HRDepartment.class;

    public HRDepartment(String name)
    {
        super(name);
    }

    @Override
    public void add(Company c)
    {

    }

    @Override
    public void remove(Company c)
    {

    }

    @Override
    public void display(int depth)
    {
        Log.i(TAG, String.format("%" + depth + "s", "").replace(" ", "-") + name);
    }

    @Override
    public void lineOfDuty()
    {
        Log.i(TAG, String.format("%s 员工招聘培训管理", name));
    }
}
