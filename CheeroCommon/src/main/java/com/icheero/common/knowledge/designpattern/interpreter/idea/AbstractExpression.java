package com.icheero.common.knowledge.designpattern.interpreter.idea;

/**
 * 声明一个抽象的解释操作，这个接口为抽象语法树种所有的节点所共享
 * Created by zuochengyao on 2018/4/3.
 */

public abstract class AbstractExpression
{
    public abstract void interpret(Context context);

}
