package com.icheero.sdk.core.reverse.dex.model;

import java.nio.charset.StandardCharsets;

import androidx.annotation.NonNull;

public class StringItem
{
    public Uleb128 val;
    public byte[] data;

    public String getDataStr()
    {
        return new String(data, StandardCharsets.UTF_8);
    }

    public int getValue()
    {
        if (val == null)
            return 0;
        else
            return (int) val.asLong();
    }

    public int getLength()
    {
        return val.getLength();
    }

    @NonNull
    @Override
    public String toString()
    {
        return "String: " + getDataStr();
    }
}
