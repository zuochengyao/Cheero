package com.icheero.sdk.basis.designpattern.facade;

public class Stock2 implements TradeAction
{

	@Override
	public void sell()
	{
		System.out.println("sell stock2");
	}

	@Override
	public void buy()
	{
		System.out.println("buy stock2");
		
	}
}