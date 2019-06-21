package com.icheero.sdk.knowledge.designpattern.behavioral.state;

public class AfternoonState extends WorkState
{
	@Override
	public void writeProgram(Work work)
	{
		if (work.getHour() < 17)
			System.out.println(String.format("当前时间：%d 下午工作 状态一般", work.getHour()));
		else
		{
			work.setWorkState(new EveningState());
			work.writeProgram();
		}
	}
}
