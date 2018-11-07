package com.icheero.sdk.knowledge.designpattern.state;

public class SleepingState extends WorkState
{
	@Override
	public void writeProgram(Work work)
	{
		System.out.println(String.format("当前时间：%d 睡觉！", work.getHour()));
	}
}
