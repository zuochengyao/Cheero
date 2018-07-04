package com.icheero.common.knowledge.designpattern.state;

public class ForenoonState extends WorkState
{
	@Override
	public void writeProgram(Work work)
	{
		if (work.getHour() < 12)
			System.out.println(String.format("当前时间：%d 上午工作 状态good", work.getHour()));
		else
		{
			work.setWorkState(new NoonState());
			work.writeProgram();
		}
	}
}
