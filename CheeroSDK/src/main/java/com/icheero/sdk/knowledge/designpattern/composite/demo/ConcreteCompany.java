package com.icheero.sdk.knowledge.designpattern.composite.demo;

import com.icheero.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuochengyao on 2018/3/19.
 */

public class ConcreteCompany extends Company
{
    private static final Class<ConcreteCompany> TAG = ConcreteCompany.class;

    public List<Company> children = new ArrayList<>();

    public ConcreteCompany(String name)
    {
        super(name);
    }

    @Override
    public void add(Company c)
    {
        children.add(c);
    }

    @Override
    public void remove(Company c)
    {
        children.add(c);
    }

    @Override
    public void display(int depth)
    {
        Log.i(TAG, String.format("%" + depth + "s", "").replace(" ", "-") + name);
        for (Company component : children)
            component.display(depth + 2);
    }

    @Override
    public void lineOfDuty()
    {
        for (Company component : children)
            component.lineOfDuty();
    }
}
