package com.icheero.common.knowledge.designpattern.interpreter.demo;

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
        com.icheero.common.util.Log.i(Scale.class, "scale = " + scale);
    }
}
