package com.icheero.sdk.knowledge.designpattern.flyweight.idea;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂，用来创建Flyweight对象。
 * 主要是用来确保合理的共享Flyweight，当用户请求一个Flyweight时，此类对象提供一个已创建的实例或创建一个。
 * Created by zuochengyao on 2018/3/29.
 */

public class FlyweightFactory
{
    private Map<String, Flyweight> flyweights = new HashMap<>();

    public FlyweightFactory()
    {
        flyweights.put("X", new ConcreteFlywight());
        flyweights.put("Y", new ConcreteFlywight());
        flyweights.put("Z", new ConcreteFlywight());
    }

    public Flyweight getFlyweight(String key)
    {
        return flyweights.get(key);
    }
}
