package com.zcy.sdk.base.designpattern.facade;

public class Realty implements TradeAction
{

	@Override
	public void sell()
	{
		System.out.println("sell realty");
	}

	@Override
	public void buy()
	{
		System.out.println("buy realty");		
	}

}
