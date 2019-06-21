package com.icheero.sdk.knowledge.designpattern.creational.builder;

abstract class PersonBuilder
{
	protected abstract void buildHead();
	protected abstract void buildBody();
	protected abstract void buildArmL();
	protected abstract void buildArmR();
	protected abstract void buildLegL();
	protected abstract void buildLegR();
}
