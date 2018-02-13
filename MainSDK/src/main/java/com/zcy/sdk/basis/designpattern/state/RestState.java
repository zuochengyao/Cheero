package com.zcy.sdk.basis.designpattern.state;

public class RestState extends WorkState
{
	@Override
	public void writeProgram(Work work)
	{
		System.out.println(String.format("当前时间：%d 下班回家", work.getHour()));
	}
}
