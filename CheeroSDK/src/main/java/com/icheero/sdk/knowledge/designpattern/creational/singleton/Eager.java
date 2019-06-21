package com.icheero.sdk.knowledge.designpattern.creational.singleton;

public class Eager
{
    private static Eager instance = new Eager();

    private Eager()
    { }

    public static Eager getInstance()
    {
        return instance;
    }
}
