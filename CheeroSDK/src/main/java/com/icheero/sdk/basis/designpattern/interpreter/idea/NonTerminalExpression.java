package com.icheero.sdk.basis.designpattern.interpreter.idea;

import com.icheero.common.util.Log;

public class NonTerminalExpression extends AbstractExpression
{
    @Override
    public void interpret(Context context)
    {
        Log.i(NonTerminalExpression.class, "非终端解释器");
    }
}
