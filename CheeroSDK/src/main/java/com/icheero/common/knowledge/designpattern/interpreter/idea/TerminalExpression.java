package com.icheero.common.knowledge.designpattern.interpreter.idea;

import com.icheero.common.util.Log;

public class TerminalExpression extends AbstractExpression
{
    @Override
    public void interpret(Context context)
    {
        Log.i(TerminalExpression.class, "终端解释器");
    }
}
