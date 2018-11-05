package com.icheero.common.knowledge.designpattern.visitor.idea;

/**
 * 为该对象结构中ConcreteElement的每一个类声明一个visit操作
 * Created by zuochengyao on 2018/4/4.
 */

public abstract class Visitor
{
    public abstract void visitConcreteElementA(ConcreteElementA elementA);

    public abstract void visitConcreteElementB(ConcreteElementB elementB);
}
