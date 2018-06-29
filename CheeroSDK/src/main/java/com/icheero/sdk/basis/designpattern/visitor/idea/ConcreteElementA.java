package com.icheero.sdk.basis.designpattern.visitor.idea;

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
