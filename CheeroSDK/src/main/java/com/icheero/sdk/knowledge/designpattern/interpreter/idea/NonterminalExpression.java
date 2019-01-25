package com.icheero.sdk.knowledge.designpattern.interpreter.idea;

import com.icheero.util.Log;

public class NonterminalExpression extends AbstractExpression
{
    @Override
    public void interpret(Context context)
    {
        Log.i(NonterminalExpression.class, "非终端解释器");
    }
}
