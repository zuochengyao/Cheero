package com.icheero.common.knowledge.designpattern.proxy;

public class Pursuit implements IGiveGift
{
	private SchoolGirl girl;
	public Pursuit(SchoolGirl girl)
	{
		this.girl = girl;
	}
	@Override
	public void giveDolls()
	{
		System.out.println("送娃娃给" + girl.getName());
	}

	@Override
	public void giveFlowers()
	{
		System.out.println("送花给" + girl.getName());

	}

	@Override
	public void giveChocolate()
	{
		System.out.println("送巧克力给" + girl.getName());
	}

}
