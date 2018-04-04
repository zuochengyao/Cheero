package com.zcy.sdk.basis.designpattern.visitor.idea;

import com.zcy.sdk.util.Log;

/**
 * 具体访问者A，实现每个由Visitor声明的操作
 * Created by zuochengyao on 2018/4/4.
 */

public class ConcreteVisitorA extends Visitor
{
    @Override
    public void visitConcreteElementA(ConcreteElementA elementA)
    {
        Log.i(ConcreteElementA.class, String.format("%s 被 %s访问", elementA, this));
    }

    @Override
    public void visitConcreteElementB(ConcreteElementB elementB)
    {
        Log.i(ConcreteElementA.class, String.format("%s 被 %s访问", elementB, this));
    }
}
