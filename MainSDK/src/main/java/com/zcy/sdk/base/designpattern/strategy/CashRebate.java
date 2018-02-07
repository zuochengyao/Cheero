package com.zcy.sdk.base.designpattern.strategy;

public class CashRebate extends CashBase
{
	private double moneyRebate;
	
	public CashRebate(double moneyRebate)
	{
		this.moneyRebate = moneyRebate;
	}
	
	@Override
	public double acceptCash(double money)
	{
		return money * moneyRebate;
	}


	
}
