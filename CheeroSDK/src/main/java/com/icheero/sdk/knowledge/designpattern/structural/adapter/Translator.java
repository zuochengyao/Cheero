package com.icheero.sdk.knowledge.designpattern.structural.adapter;

public class Translator extends Player
{
	private ChineseCenter chineseCenter;
	
	public Translator(String name)
	{
		super(name);
		chineseCenter = new ChineseCenter(name);
	}
	@Override
	public void attach()
	{
		chineseCenter.gongji();
	}
	@Override
	public void defence()
	{
		chineseCenter.fangshou();
	}
}
