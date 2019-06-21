package com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo;

import java.util.ArrayList;
import java.util.List;

public class PersonStructure
{
    private List<Person> elements = new ArrayList<>();

    public void attach(Person element)
    {
        elements.add(element);
    }

    public void detach(Person element)
    {
        elements.remove(element);
    }

    public void accept(Action visitor)
    {
        for (Person e : elements)
            e.accept(visitor);
    }
}
