package com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.idea;

import com.icheero.sdk.util.Log;

public class TerminalExpression extends AbstractExpression
{
    @Override
    public void interpret(Context context)
    {
        Log.i(TerminalExpression.class, "终端解释器");
    }
}
