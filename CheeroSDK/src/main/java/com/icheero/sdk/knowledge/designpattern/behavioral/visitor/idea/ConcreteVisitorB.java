package com.icheero.sdk.knowledge.designpattern.behavioral.visitor.idea;

import com.icheero.sdk.util.Log;

/**
 * 具体访问者A，实现每个由Visitor声明的操作
 * Created by zuochengyao on 2018/4/4.
 */

public class ConcreteVisitorB extends Visitor
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
