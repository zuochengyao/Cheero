package com.icheero.sdk.core.reverse.dex.model;

import java.nio.charset.StandardCharsets;

import androidx.annotation.NonNull;

public class StringItem
{
    public byte val;
    public byte[] data;

    public String getDataStr()
    {
        return new String(data, StandardCharsets.UTF_8);
    }

    @NonNull
    @Override
    public String toString()
    {
        return "DataStr: " + getDataStr();
    }
}
