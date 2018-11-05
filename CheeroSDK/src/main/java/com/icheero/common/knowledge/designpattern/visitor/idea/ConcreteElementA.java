package com.icheero.common.knowledge.designpattern.visitor.idea;

public class ConcreteElementA extends Element
{
    @Override
    public void accept(Visitor visitor)
    {
        visitor.visitConcreteElementA(this);
    }

    public void operationA()
    {
    }
}
