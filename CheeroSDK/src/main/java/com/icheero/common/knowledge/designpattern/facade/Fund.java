package com.icheero.common.knowledge.designpattern.facade;

public class Fund implements TradeAction
{
	private Stock1 stock1;
	private Stock2 stock2;
	private Realty realty;
	
	public Fund()
	{
		stock1 = new Stock1();
		stock2 = new Stock2();
		realty = new Realty();
	}
	
	@Override
	public void sell()
	{
		stock1.sell();
		stock2.sell();
		realty.sell();
	}

	@Override
	public void buy()
	{
		stock1.buy();
		stock2.buy();
		realty.buy();
	}

}
