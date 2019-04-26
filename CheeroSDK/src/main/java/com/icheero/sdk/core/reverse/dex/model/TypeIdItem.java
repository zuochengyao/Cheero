package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

public class TypeIdItem
{
    public byte[] descriptorIdx = new byte[4];

    public static int getLength()
    {
        return 4;
    }

    public int getDescriptorIdx()
    {
        return FileUtils.byte2Int(descriptorIdx);
    }

    @NonNull
    @Override
    public String toString()
    {
        return "TypeId: " + FileUtils.byte2HexString(descriptorIdx) + "(" + getDescriptorIdx() + ")";
    }
}
