package com.zcy.sdk.basis.designpattern.visitor.idea;

/**
 * 定义一个Accept操作，它以一个访问者为参数
 * Created by zuochengyao on 2018/4/4.
 */

public abstract class Element
{
    public abstract void accept(Visitor visitor);
}
