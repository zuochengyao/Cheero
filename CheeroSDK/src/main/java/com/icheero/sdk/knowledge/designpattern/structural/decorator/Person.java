package com.icheero.sdk.knowledge.designpattern.structural.decorator;

public class Person
{
	private String name;

	Person()
	{
	}

	Person(String name)
	{
		this.name = name;
	}

	public void show()
	{
		System.out.println(String.format("装扮的{%s}", name));
	}
}
