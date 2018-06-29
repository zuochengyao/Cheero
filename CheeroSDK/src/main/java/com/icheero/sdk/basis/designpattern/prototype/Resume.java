package com.icheero.sdk.basis.designpattern.prototype;

public class Resume implements Cloneable
{
	private String name;
	private String gender;
	private int age;

	private WorkExperience we;

	public Resume(String name)
	{
		this.name = name;
		we = new WorkExperience();
	}

	private Resume(WorkExperience we)
	{
		try
		{
			this.we = (WorkExperience) we.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
	}

	public void setPeronalInfo(String gender, int age)
	{
		this.gender = gender;
		this.age = age;
	}

	public void setWorkExperience(String timeArea, String company)
	{
		we.setTimeArea(timeArea);
		we.setCompany(company);
	}

	public void display()
	{
		System.out.println(String.format("%s %s %d", name, gender, age));
		System.out.println(String.format("工作经历：%s %s", we.getTimeArea(), we.getCompany()));
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Resume resume = new Resume(this.we);
		resume.name = this.name;
		resume.gender = this.gender;
		resume.age = this.age;
		return resume;
	}
}
