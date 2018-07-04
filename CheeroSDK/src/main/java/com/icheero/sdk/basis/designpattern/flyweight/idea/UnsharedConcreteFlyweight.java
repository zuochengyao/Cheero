package com.icheero.sdk.basis.designpattern.flyweight.idea;

import com.icheero.common.util.Log;

/**
 * 不需要共享的子类
 * Created by zuochengyao on 2018/3/29.
 */

public class UnsharedConcreteFlyweight extends Flyweight
{
    @Override
    public void operation(int extrinsicState)
    {
        Log.i(UnsharedConcreteFlyweight.class, "不具体Flyweight：" + extrinsicState);
    }
}
