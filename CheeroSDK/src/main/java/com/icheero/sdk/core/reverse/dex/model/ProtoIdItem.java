package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.core.reverse.dex.DexParser;
import com.icheero.sdk.util.IOUtils;

import androidx.annotation.NonNull;

public class ProtoIdItem
{
    public byte[] shortyIdx = new byte[4];
    public byte[] returnTypeIdx = new byte[4];
    public byte[] parametersOff = new byte[4];
    public TypeItemList parameters = new TypeItemList();

    public static int getLength()
    {
        return 12;
    }

    public int getShortyIdxValue()
    {
        return IOUtils.byte2Int(shortyIdx);
    }

    public int getReturnTypeIdxValue()
    {
        return IOUtils.byte2Int(returnTypeIdx);
    }

    public int getParametersOffValue()
    {
        return IOUtils.byte2Int(parametersOff);
    }

    public String getReturnType()
    {
        return DexParser.getInstance().getTypeString(getReturnTypeIdxValue());
    }

    public String getParametersType()
    {
        return parameters.toString();
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(getReturnType()).append(" ").append("(");
        if (getParametersOffValue() > 0)
            builder.append(getParametersType());
        builder.append(")");
        return builder.toString();
    }
}
