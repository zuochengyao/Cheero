package com.icheero.sdk.knowledge.designpattern.creational.builder;

public class PersonDirector
{
	private PersonBuilder pb;
	
	public PersonDirector(PersonBuilder pb)
	{
		this.pb = pb;
	}
	
	public void buildPerson()
	{
		pb.buildBody();
		pb.buildArmL();
		pb.buildArmR();
		pb.buildHead();
		pb.buildLegL();
		pb.buildLegR();
	}
}
