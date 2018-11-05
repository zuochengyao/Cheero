package com.icheero.common.knowledge.designpattern.decorator;

public class Person
{
	private String name;

	public Person()
	{
	}

	public Person(String name)
	{
		this.name = name;
	}

	public void show()
	{
		System.out.println(String.format("装扮的{%s}", name).toString());
	}
}
