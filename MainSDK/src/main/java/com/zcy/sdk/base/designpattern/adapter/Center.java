package com.zcy.sdk.base.designpattern.adapter;

public class Center extends Player
{

	public Center(String name)
	{
		super(name);
	}

	@Override
	public void attach()
	{
		System.out.println(name + "扣篮");
	}

	@Override
	public void defence()
	{
		System.out.println(name + "盖帽");
	}

}
