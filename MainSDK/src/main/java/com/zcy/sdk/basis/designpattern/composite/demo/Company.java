package com.zcy.sdk.basis.designpattern.composite.demo;

/**
 * 公司类 抽象类或接口
 * Created by zuochengyao on 2018/3/19.
 */

public abstract class Company
{
    protected String name;

    public Company(String name)
    {
        this.name = name;
    }

    public abstract void add(Company c);
    public abstract void remove(Company c);
    public abstract void display(int depth); // 显示
    public abstract void lineOfDuty(); // 旅行职责
}
