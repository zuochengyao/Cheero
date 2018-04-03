package com.zcy.sdk.basis.designpattern.flyweight.demo;

import com.zcy.sdk.util.Log;

/**
 * 具体网站类
 * Created by zuochengyao on 2018/4/3.
 */

public class ConcreteWebSite extends WebSite
{
    private String name;

    public ConcreteWebSite(String name)
    {
        this.name = name;
    }

    @Override
    public void setUser(User user)
    {
        Log.i(ConcreteWebSite.class, "网站分类：" + name + "用户：" + user.getName());
    }
}
