package com.icheero.sdk.knowledge.designpattern.structural.proxy;

public class Proxy implements IGiveGift
{
	private Pursuit boy;
	
	public Proxy(SchoolGirl girl)
	{
		boy = new Pursuit(girl);
	}
	
	@Override
	public void giveDolls()
	{
		boy.giveDolls();
	}

	@Override
	public void giveFlowers()
	{
		boy.giveFlowers();
	}

	@Override
	public void giveChocolate()
	{
		boy.giveChocolate();
	}

}
