package com.icheero.common.knowledge.designpattern.strategy;

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
