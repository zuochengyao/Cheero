package com.icheero.sdk.knowledge.designpattern.bridge;

/**
 * Created by zuochengyao on 2018/3/20.
 */

public class Abstraction
{
    protected Implementor implementor;

    public void setImplementor(Implementor implementor)
    {
        this.implementor = implementor;
    }

    public void operation()
    {
        implementor.operation();
    }
}
