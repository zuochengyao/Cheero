package com.icheero.sdk.knowledge.designpattern.bridge;

import com.icheero.util.Log;

/**
 * Created by zuochengyao on 2018/3/20.
 */

public class ConcreteImpB implements Implementor
{
    @Override
    public void operation()
    {
        Log.i(ConcreteImpB.class, "Operation B");
    }
}
