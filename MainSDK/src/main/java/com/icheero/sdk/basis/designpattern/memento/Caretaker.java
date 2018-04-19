package com.icheero.sdk.basis.designpattern.memento;

/**
 * 管理者：
 *
 * 负责保存好备忘录，不能对备忘录的内容进行操作或检查
 * Created by zuochengyao on 2018/3/16.
 */

public class Caretaker
{
    private Memento memento;

    public Memento getMemento()
    {
        return memento;
    }

    public void setMemento(Memento memento)
    {
        this.memento = memento;
    }

}
