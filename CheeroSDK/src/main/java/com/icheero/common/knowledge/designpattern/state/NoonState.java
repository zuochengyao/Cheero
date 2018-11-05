package com.icheero.common.knowledge.designpattern.state;

public class NoonState extends WorkState
{
	@Override
	public void writeProgram(Work work)
	{
		if (work.getHour() < 13)
			System.out.println(String.format("当前时间：%d 午饭时间 然后午休", work.getHour()));
		else
		{
			work.setWorkState(new AfternoonState());
			work.writeProgram();
		}
	}
}
