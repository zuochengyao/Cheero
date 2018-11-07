package com.icheero.sdk.knowledge.designpattern.adapter;

public class Guard extends Player
{

	public Guard(String name)
	{
		super(name);
	}

	@Override
	public void attach()
	{
		System.out.println(name + "投三分");
	}

	@Override
	public void defence()
	{
		System.out.println(name + "抢断");
	}

}
