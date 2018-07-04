package com.icheero.common.knowledge.designpattern.facade;

public class Stock1 implements TradeAction
{

	@Override
	public void sell()
	{
		System.out.println("sell stock1");
	}

	@Override
	public void buy()
	{
		System.out.println("buy stock1");
		
	}
}
