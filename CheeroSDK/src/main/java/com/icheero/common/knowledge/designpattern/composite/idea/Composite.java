package com.icheero.common.knowledge.designpattern.composite.idea;

import com.icheero.common.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义有枝节点行为，用来存储子部件，在Component接口中实现与子部件有关的操作（比如add、remove）
 * Created by zuochengyao on 2018/3/16.
 */

public class Composite extends Component
{
    private List<Component> children = new ArrayList<>();

    public Composite(String name)
    {
        super(name);
    }

    @Override
    public void add(Component component)
    {
        children.add(component);
    }

    @Override
    public void remove(Component component)
    {
        children.remove(component);
    }

    @Override
    public void display(int depth)
    {
        Log.i(Composite.class, String.format("%" + depth + "s", "").replace(" ", "-") + name);
        for (Component component : children)
            component.display(depth + 2);
    }
}
