package com.zcy.sdk.base.designpattern.observer;

public class StockObserver extends Observer implements IListener
{
	public StockObserver(String name)
	{
		super(name);
	}
	
	private void closeStock()
	{
		System.out.println(getName() + "关闭股票界面");
	}

	@Override
	public void notifyMe()
	{
		closeStock();
	}
}
