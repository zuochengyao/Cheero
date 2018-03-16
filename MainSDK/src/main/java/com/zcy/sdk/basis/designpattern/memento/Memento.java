package com.zcy.sdk.basis.designpattern.memento;

/**
 * 备忘录:
 *
 * 负责存储Originator对象的内部状态，并防止Originator意外的其他对象访问备忘录
 * 备忘录有两个接口，Caretaker只能看到备忘录的窄接口，它只能将备忘录传递给其他对象
 * Originator能看到一个宽接口，允许它访问返回到先前状态所需的所有数据
 * Created by zuochengyao on 2018/3/16.
 */

public class Memento
{
    private String _state;

    public Memento(String state)
    {
        this._state = state;
    }

    public String getState()
    {
        return _state;
    }
}
