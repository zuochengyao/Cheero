package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.core.reverse.dex.DexParser;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

public class TypeItem
{
    public byte[] typeIdx = new byte[2];

    private short getTypeIdxValue()
    {
        return FileUtils.byte2Short(typeIdx);
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public String toString()
    {
        String type = DexParser.getInstance().getTypeString(getTypeIdxValue());
        return Common.signature2JavaType(type);
    }
}
