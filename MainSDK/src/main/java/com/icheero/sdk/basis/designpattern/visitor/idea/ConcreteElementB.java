package com.icheero.sdk.basis.designpattern.visitor.idea;

public class ConcreteElementB extends Element
{
    @Override
    public void accept(Visitor visitor)
    {
        visitor.visitConcreteElementB(this);
    }

    public void operationB()
    {
    }
}
