package com.icheero.sdk.basis.designpattern.composite.demo;

import com.icheero.sdk.util.Log;

/**
 * Created by zuochengyao on 2018/3/19.
 */

public class FinanceDepartment extends Company
{
    private static final Class<FinanceDepartment> TAG = FinanceDepartment.class;

    public FinanceDepartment(String name)
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
        Log.i(TAG, String.format("%s 公司财务收支管理", name));
    }
}
