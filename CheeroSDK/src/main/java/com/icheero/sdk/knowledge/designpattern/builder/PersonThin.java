package com.icheero.sdk.knowledge.designpattern.builder;

public class PersonThin extends PersonBuilder
{

	@Override
	protected void buildHead()
	{
		System.out.println("小脑袋");
	}

	@Override
	protected void buildBody()
	{
		System.out.println("小身子");
	}

	@Override
	protected void buildArmL()
	{
		System.out.println("瘦胳膊-左");
	}

	@Override
	protected void buildArmR()
	{
		System.out.println("瘦胳膊-右");
	}

	@Override
	protected void buildLegL()
	{
		System.out.println("瘦腿-左");
	}

	@Override
	protected void buildLegR()
	{
		System.out.println("瘦腿-右");
	}

}
