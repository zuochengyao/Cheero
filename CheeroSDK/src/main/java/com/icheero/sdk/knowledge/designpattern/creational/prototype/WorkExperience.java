package com.icheero.sdk.knowledge.designpattern.creational.prototype;

public class WorkExperience implements Cloneable
{
    private String timeArea;
    private String company;

    String getCompany()
    {
        return company;
    }

    void setCompany(String company)
    {
        this.company = company;
    }

    String getTimeArea()
    {
        return timeArea;
    }

    void setTimeArea(String timeArea)
    {
        this.timeArea = timeArea;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
