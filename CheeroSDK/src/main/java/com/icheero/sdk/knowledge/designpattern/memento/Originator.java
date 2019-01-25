package com.icheero.sdk.knowledge.designpattern.memento;

import com.icheero.util.Log;

/**
 * 发起人：
 * 负责创建一个备忘录Memento，用以记录当前时刻它的内部状态，并可使用备忘录回复内部状态
 * 可根据需要决定Memento存储Originator的哪些内部状态
 * Created by zuochengyao on 2018/3/16.
 */

public class Originator
{
    private String _state;

    public String getState()
    {
        return _state;
    }

    public void setState(String _state)
    {
        this._state = _state;
    }

    public Memento createMemento()
    {
        return new Memento(_state);
    }

    public void setMemento(Memento memento)
    {
        _state = memento.getState();
    }

    public void show()
    {
        Log.i(Originator.class, "State = " + _state);
    }
}
