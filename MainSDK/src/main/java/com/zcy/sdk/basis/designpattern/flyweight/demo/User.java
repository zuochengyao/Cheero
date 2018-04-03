package com.zcy.sdk.basis.designpattern.flyweight.demo;

/**
 * 用户类：用户网站的账号，外部状态
 * Created by zuochengyao on 2018/4/3.
 */

public class User
{
    private String name;

    public User(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
