package com.zcy.sdk.basis.designpattern.interpreter.idea;

import com.zcy.sdk.util.Log;

public class NonterminalExpression extends AbstractExpression
{
    @Override
    public void interpret(Context context)
    {
        Log.i(NonterminalExpression.class, "非终端解释器");
    }
}
