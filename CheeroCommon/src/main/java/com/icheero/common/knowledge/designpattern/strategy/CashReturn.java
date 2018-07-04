package com.icheero.common.knowledge.designpattern.strategy;

public class CashReturn extends CashBase
{
	private double moneyCondition;
	private double moneyReturn;
	
	public CashReturn(double moneyCondition, double moneyReturn)
	{
		this.moneyCondition = moneyCondition;
		this.moneyReturn = moneyReturn;
	}
	
	@Override
	public double acceptCash(double money)
	{
		if (money >= moneyCondition)
			money = money - Math.floor(money / moneyCondition) * moneyReturn;
		return money;
	}
}
