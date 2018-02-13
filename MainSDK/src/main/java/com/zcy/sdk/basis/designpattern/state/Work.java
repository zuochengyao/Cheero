package com.zcy.sdk.basis.designpattern.state;

public class Work
{
	private WorkState workState;
	private int hour;
	private boolean isFinished;
	
	public Work()
	{
		workState = new ForenoonState();
	}
	
	public int getHour()
	{
		return hour;
	}
	public void setHour(int hour)
	{
		this.hour = hour;
	}
	public boolean isFinished()
	{
		return isFinished;
	}
	public void setFinished(boolean isFinished)
	{
		this.isFinished = isFinished;
	}

	public void setWorkState(WorkState state)
	{
		this.workState = state;
	}
	
	public void writeProgram()
	{
		if (workState != null)
			workState.writeProgram(this);
	}
}
