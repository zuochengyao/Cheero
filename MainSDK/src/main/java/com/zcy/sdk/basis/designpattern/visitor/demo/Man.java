package com.zcy.sdk.basis.designpattern.visitor.demo;

public class Man extends Person
{
    @Override
    public void accept(Action visitor)
    {
        visitor.getConclusionMan(this);
    }
}
