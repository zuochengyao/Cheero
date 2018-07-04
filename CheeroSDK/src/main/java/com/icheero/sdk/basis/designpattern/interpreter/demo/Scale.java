package com.icheero.sdk.basis.designpattern.interpreter.demo;

import com.icheero.common.util.Log;

public class Scale extends Expression
{
    @Override
    public void execute(String key, double value)
    {
        String scale = "";
        switch ((int) value)
        {
            case 1:
                scale = "低音";
                break;
            case 2:
                scale = "中音";
                break;
            case 3:
                scale = "高音";
                break;
        }
        Log.i(Scale.class, "scale：" + scale);
    }
}
