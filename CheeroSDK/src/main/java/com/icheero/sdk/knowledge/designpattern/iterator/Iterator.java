package com.icheero.sdk.knowledge.designpattern.iterator;

/**
 * Created by zuochengyao on 2018/3/19.
 */

public abstract class Iterator
{
    public abstract Object first();
    public abstract Object next();
    public abstract boolean isFinish();
    public abstract Object currentItem();
}
