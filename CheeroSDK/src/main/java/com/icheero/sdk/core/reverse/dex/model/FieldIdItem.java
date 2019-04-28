package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.core.reverse.dex.DexParser;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

public class FieldIdItem
{
    /** field 所属的 class 类型, class_idx 的值是 type_ids 的一个 index，并且必须指向一个class 类型 */
    public byte[] classIdx = new byte[2];
    /** field 的类型, 它的值也是 type_ids 的一个 index */
    public byte[] typeIdx = new byte[2];
    /** field 的名称，它的值是 string_ids 的一个 index */
    public byte[] nameIdx = new byte[4];

    public short getClassIdxValue()
    {
        return FileUtils.byte2Short(classIdx);
    }

    public short getTypeIdxValue()
    {
        return FileUtils.byte2Short(typeIdx);
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
        builder.append(Common.signature2JavaType(DexParser.getTypeString(getTypeIdxValue()))).append(" ");
        builder.append(Common.signature2JavaType(DexParser.getTypeString(getClassIdxValue()))).append(".");
        builder.append(DexParser.getDataString(getNameIdxValue()));
        return builder.toString();
    }
}
