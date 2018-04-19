package com.icheero.sdk.basis.designpattern.responsibility;

/**
 * Created by zuochengyao on 2018/3/21.
 */

public class WorkRequest
{
    public static final String TYPE_HOLIDAY = "请假";
    public static final String TYPE_MONEY = "加薪";

    private String type;
    private String content;
    private int number;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }
}
