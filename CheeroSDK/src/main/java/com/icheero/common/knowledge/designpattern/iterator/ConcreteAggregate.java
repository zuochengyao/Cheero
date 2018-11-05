package com.icheero.common.knowledge.designpattern.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuochengyao on 2018/3/19.
 */

public class ConcreteAggregate extends Aggregate
{
    private List<Object> items = new ArrayList<>();

    @Override
    public Iterator createIterator()
    {
        return new ConcreteIterator(this);
    }

    public int size()
    {
        return items.size();
    }

    public Object getItem(int index)
    {
        return items.get(index);
    }

    public void setItem(int index, Object obj)
    {
        items.add(index, obj);
    }

}
