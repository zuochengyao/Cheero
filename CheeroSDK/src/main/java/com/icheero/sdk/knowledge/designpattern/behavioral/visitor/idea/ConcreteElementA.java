package com.icheero.sdk.knowledge.designpattern.behavioral.visitor.idea;

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
