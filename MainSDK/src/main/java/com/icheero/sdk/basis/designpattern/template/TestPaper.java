package com.icheero.sdk.basis.designpattern.template;

public abstract class TestPaper
{
	public void question1()
	{
		System.out.println("大象装冰箱一共几步？a.1 b.2 c.3 d.4");
		System.out.println("答案：" + answer1());
	}
	
	public void question2()
	{
		System.out.println("动物园开会，哪个动物没去？a.大象 b.狗 c.猫 d.张静雅");
		System.out.println("答案：" + answer2());
	}
	
	protected abstract String answer1();
	
	protected abstract String answer2();
}
