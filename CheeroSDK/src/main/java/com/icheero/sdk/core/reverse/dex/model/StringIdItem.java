package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.util.IOUtils;

import androidx.annotation.NonNull;

public class StringIdItem
{
    public byte[] stringDataOff = new byte[4];
    public StringItem stringData = new StringItem();

    public static int getLength()
    {
        return 4;
    }

    public int getStringDataOffValue()
    {
        return IOUtils.byte2Int(stringDataOff);
    }

    @NonNull
    @Override
    public String toString()
    {
        String str = "StringId: " + IOUtils.byte2HexString(stringDataOff) + "(" + getStringDataOffValue() + ")";
        if (stringData.data != null)
            str += ", " + stringData.toString();
        return str;
    }
}
