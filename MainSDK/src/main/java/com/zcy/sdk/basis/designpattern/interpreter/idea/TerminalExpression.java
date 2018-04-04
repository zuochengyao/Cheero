package com.zcy.sdk.basis.designpattern.interpreter.idea;

import com.zcy.sdk.util.Log;

public class TerminalExpression extends AbstractExpression
{
    @Override
    public void interpret(Context context)
    {
        Log.i(TerminalExpression.class, "终端解释器");
    }
}
