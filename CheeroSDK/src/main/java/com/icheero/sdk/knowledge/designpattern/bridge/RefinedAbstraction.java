package com.icheero.sdk.knowledge.designpattern.bridge;

/**
 * Created by zuochengyao on 2018/3/20.
 */

public class RefinedAbstraction extends Abstraction
{
    @Override
    public void operation()
    {
        implementor.operation();
    }
}
