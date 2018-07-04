package com.icheero.common.knowledge.designpattern.strategy;

public class CashContext
{	
	public enum CashType
	{
		NORMAL, RETURN, REBATE;
	}
	private CashBase cb;
	
	public CashContext(CashType type)
	{
		switch (type)
		{
			case NORMAL:
			default:
				cb = new CashBase();
				break;
			case RETURN:
				cb = new CashReturn(300, 100);
				break;
			case REBATE:
				cb = new CashRebate(0.8);
				break;
		}
	}
	
	public double getResult(double money)
	{
		return cb.acceptCash(money);
	}
	
}
