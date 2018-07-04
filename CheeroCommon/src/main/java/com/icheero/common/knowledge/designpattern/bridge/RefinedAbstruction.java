package com.icheero.common.knowledge.designpattern.bridge;

/**
 * Created by zuochengyao on 2018/3/20.
 */

public class RefinedAbstruction extends Abstraction
{
    @Override
    public void operation()
    {
        implementor.operation();
    }
}
