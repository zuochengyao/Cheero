package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.core.reverse.dex.DexParser;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

public class MethodIdItem
{
    /** method 所属的 class 类型, class_idx 的值是 type_ids 的一个 index，并且必须指向一个class 类型 */
    public byte[] classIdx = new byte[2];
    /** method 的类型, 它的值也是 proto_ids 的一个 index */
    public byte[] protoIdx = new byte[2];
    /** method 的名称，它的值是 string_ids 的一个 index */
    public byte[] nameIdx = new byte[4];

    public short getClassIdxValue()
    {
        return FileUtils.byte2Short(classIdx);
    }

    public short getProtoIdxValue()
    {
        return FileUtils.byte2Short(protoIdx);
    }

    public int getNameIdxValue()
    {
        return FileUtils.byte2Int(nameIdx);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(Common.signature2JavaType(DexParser.getInstance().getProtoIdItem(getProtoIdxValue()).getReturnType())).append(" ");
        builder.append(Common.signature2JavaType(DexParser.getInstance().getTypeString(getClassIdxValue()))).append(".").append(DexParser.getInstance().getDataString(getNameIdxValue()));
        builder.append("(");
        builder.append(DexParser.getInstance().getProtoIdItem(getProtoIdxValue()).getParametersType());
        builder.append(")");
        return builder.toString();
    }
}
