package com.zcy.sdk.base.designpattern.adapter;

public class ChineseCenter
{
	String name;

	public ChineseCenter(String name)
	{
		this.name = name;
	}

	public void gongji()
	{
		System.out.println(name + "扣篮");
	}

	public void fangshou()
	{
		System.out.println(name + "盖帽");
	}
}
