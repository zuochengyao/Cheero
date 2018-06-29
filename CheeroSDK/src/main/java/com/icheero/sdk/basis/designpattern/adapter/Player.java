package com.icheero.sdk.basis.designpattern.adapter;

public abstract class Player
{
	protected String name;
	
	public Player(String name)
	{
		this.name = name;
	}
	
	public abstract void attach();
	public abstract void defence();
}
