package com.icheero.sdk.knowledge.designpattern.behavioral.observer;

public class NBAObserver extends Observer implements IListener
{
	public NBAObserver(String name)
	{
		super(name);
	}
	
	private void closeNBALive()
	{
		System.out.println(getName() + "关闭NBA直播");
	}

	@Override
	public void notifyMe()
	{
		closeNBALive();
	}

}
