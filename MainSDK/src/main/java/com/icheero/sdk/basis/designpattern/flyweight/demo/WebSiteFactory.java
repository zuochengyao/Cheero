package com.icheero.sdk.basis.designpattern.flyweight.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 网站工厂类
 * Created by zuochengyao on 2018/4/3.
 */

public class WebSiteFactory
{
    private Map<String, ConcreteWebSite> flyweights = new HashMap<>();

    public WebSite getWebSiteCategory(String key)
    {
        if (!flyweights.containsKey(key))
            flyweights.put(key, new ConcreteWebSite(key));
        return flyweights.get(key);
    }

    public int getWebSiteCount()
    {
        return flyweights.size();
    }
}
