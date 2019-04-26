package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.core.reverse.dex.DexParser;
import com.icheero.sdk.util.FileUtils;

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
        return FileUtils.byte2Int(shortyIdx);
    }

    public int getReturnTypeIdxValue()
    {
        return FileUtils.byte2Int(returnTypeIdx);
    }

    public int getParametersOffValue()
    {
        return FileUtils.byte2Int(parametersOff);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("shortyIdx: ").append(DexParser.getDataString(getShortyIdxValue())).append(" (").append(getShortyIdxValue()).append(")").append(", ");
        builder.append("returnTypeIdx: ").append(DexParser.getTypeString(getReturnTypeIdxValue())).append(" (").append(getReturnTypeIdxValue()).append(")").append(", ");
        if (getParametersOffValue() > 0)
            builder.append("parameters: ").append(parameters.toString());
        else
            builder.append("parameters: null");
        return builder.toString();
    }
}
