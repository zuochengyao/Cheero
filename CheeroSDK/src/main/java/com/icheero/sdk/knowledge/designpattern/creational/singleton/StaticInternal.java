package com.icheero.sdk.knowledge.designpattern.creational.singleton;

public class StaticInternal
{
    private StaticInternal()
    { }

    public static StaticInternal getInstance()
    {
        return StaticInternalHolder.mInstance;
    }

    private static class StaticInternalHolder
    {
        private static final StaticInternal mInstance = new StaticInternal();
    }
}
