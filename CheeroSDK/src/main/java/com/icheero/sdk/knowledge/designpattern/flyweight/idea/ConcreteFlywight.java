package com.icheero.sdk.knowledge.designpattern.flyweight.idea;

import com.icheero.util.Log;

/**
 * 实现类，并未内部状态增加存储空间
 * Created by zuochengyao on 2018/3/29.
 */

public class ConcreteFlywight extends Flyweight
{
    @Override
    public void operation(int extrinsicState)
    {
        Log.i(ConcreteFlywight.class, "具体Flyweight：" + extrinsicState);
    }
}
