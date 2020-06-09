package com.icheero.app.model;

public class MessageEvent
{
    private String mMessage;

    public MessageEvent(String message)
    {
        mMessage = message;
    }

    public String getMessage()
    {
        return mMessage;
    }

    public void setMessage(String message)
    {
        mMessage = message;
    }
}
