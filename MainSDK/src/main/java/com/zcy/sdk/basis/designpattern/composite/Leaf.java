package com.zcy.sdk.basis.designpattern.composite;

import com.zcy.sdk.util.Log;

/**
 * 在组合中表示叶节点对象，叶节点没有子节点
 * Created by zuochengyao on 2018/3/16.
 */

public class Leaf extends Component
{
    private static final Class<Leaf> TAG = Leaf.class;

    public Leaf(String name)
    {
        super(name);
    }

    @Override
    public void add(Component component)
    {
        Log.i(TAG, "Cannot add to a leaf");
    }

    @Override
    public void remove(Component component)
    {
        Log.i(TAG, "Cannot remove to a leaf");
    }

    @Override
    public void display(int depth)
    {
        Log.i(TAG, String.format("%" + depth + "s", "").replace(" ", "-") + name);
    }
}
