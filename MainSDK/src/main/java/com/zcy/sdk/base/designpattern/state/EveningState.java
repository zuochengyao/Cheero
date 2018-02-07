package com.zcy.sdk.base.designpattern.state;

public class EveningState extends WorkState
{
	@Override
	public void writeProgram(Work work)
	{
		if (work.isFinished())
		{
			work.setWorkState(new RestState());
			work.writeProgram();
		}
		else
		{
			if (work.getHour() < 21)
				System.out.println(String.format("当前时间：%d 加班ing 状态不好", work.getHour()));
			else
			{
				work.setWorkState(new SleepingState());
				work.writeProgram();
			}
		}
	}
}
