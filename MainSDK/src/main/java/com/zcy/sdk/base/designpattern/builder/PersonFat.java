package com.zcy.sdk.base.designpattern.builder;

public class PersonFat extends PersonBuilder
{

	@Override
	protected void buildHead()
	{
		System.out.println("大脑袋");
	}

	@Override
	protected void buildBody()
	{
		System.out.println("大身子");
	}

	@Override
	protected void buildArmL()
	{
		System.out.println("胖胳膊-左");
	}

	@Override
	protected void buildArmR()
	{
		System.out.println("胖胳膊-右");
	}

	@Override
	protected void buildLegL()
	{
		System.out.println("胖腿-左");
	}

	@Override
	protected void buildLegR()
	{
		System.out.println("胖腿-右");
	}

}
