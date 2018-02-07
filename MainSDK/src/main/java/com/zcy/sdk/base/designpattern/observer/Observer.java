package com.zcy.sdk.base.designpattern.observer;

public class Observer
{
	private String name;

	protected Observer(String name)
	{
		this.setName(name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
