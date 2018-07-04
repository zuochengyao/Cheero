package com.icheero.common.knowledge.designpattern.interpreter.idea;

import com.icheero.common.util.Log;

public class NonterminalExpression extends AbstractExpression
{
    @Override
    public void interpret(Context context)
    {
        Log.i(NonterminalExpression.class, "非终端解释器");
    }
}
