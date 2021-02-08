package com.icheero.sdk.knowledge.designpattern.structural.proxy.dynamic;

public class OrdinaryStudent implements IStudent
{
    @Override
    public void eat()
    {
        System.out.println("吃饭");
    }

    @Override
    public void write()
    {
        System.out.println("写作文");
    }
}
