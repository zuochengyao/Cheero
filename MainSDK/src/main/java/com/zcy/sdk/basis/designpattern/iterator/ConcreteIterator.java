package com.zcy.sdk.basis.designpattern.iterator;

/**
 * Created by zuochengyao on 2018/3/19.
 */

public class ConcreteIterator extends Iterator
{
    private ConcreteAggregate aggregate;
    private int current = 0;

    public ConcreteIterator(ConcreteAggregate aggregate)
    {
        this.aggregate = aggregate;
    }

    @Override
    public Object first()
    {
        return aggregate.getItem(0);
    }

    @Override
    public Object next()
    {
        Object ret = null;
        current++;
        if (current < aggregate.size())
            ret = aggregate.getItem(current);
        return ret;
    }

    @Override
    public boolean isFinish()
    {
        return current >= aggregate.size();
    }

    @Override
    public Object currentItem()
    {
        return aggregate.getItem(current);
    }
}
