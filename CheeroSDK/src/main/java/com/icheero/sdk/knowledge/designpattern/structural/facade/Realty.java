package com.icheero.sdk.knowledge.designpattern.structural.facade;

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
