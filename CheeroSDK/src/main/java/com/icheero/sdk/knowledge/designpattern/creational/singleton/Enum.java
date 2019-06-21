package com.icheero.sdk.knowledge.designpattern.creational.singleton;

/**
 * 默认枚举实例的创建是线程安全的，并且在任何情况下都是单例。
 */
public enum Enum
{
    INSTANCE;
    public void doSomething()
    {}
}
