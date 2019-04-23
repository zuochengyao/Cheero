package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

public class DexHeader
{
    public byte[] magic = new byte[8];
    public byte[] checkSum = new byte[4];
    public byte[] signature = new byte[20];
    public byte[] fileSize = new byte[4];
    public byte[] headerSize = new byte[4];
    public byte[] endianTag = new byte[4];
    public byte[] linkSize = new byte[4];
    public byte[] linkOff = new byte[4];
    public byte[] mapOff = new byte[4];
    public byte[] stringIdsSize = new byte[4];
    public byte[] stringIdsOff = new byte[4];
    public byte[] typeIdsSize = new byte[4];
    public byte[] typeIdsOff = new byte[4];
    public byte[] protoIdsSize = new byte[4];
    public byte[] protoIdsOff = new byte[4];
    public byte[] fieldIdsSize = new byte[4];
    public byte[] fieldIdsOff = new byte[4];
    public byte[] methodIdsSize = new byte[4];
    public byte[] methodIdsOff = new byte[4];
    public byte[] classDefsSize = new byte[4];
    public byte[] classDefsOff = new byte[4];
    public byte[] dataSize = new byte[4];
    public byte[] dataOff = new byte[4];

    public int getHeaderLength()
    {
        return 112;
    }

    public int getCheckSumValue()
    {
        return FileUtils.byte2Int(checkSum);
    }

    public int getFileSizeValue()
    {
        return FileUtils.byte2Int(fileSize);
    }

    public int getHeaderSizeValue()
    {
        return FileUtils.byte2Int(headerSize);
    }

    public int getEndianTagValue()
    {
        return FileUtils.byte2Int(endianTag);
    }

    public int getLinkSizeValue()
    {
        return FileUtils.byte2Int(linkSize);
    }

    public int getLinkOffValue()
    {
        return FileUtils.byte2Int(linkOff);
    }

    public int getMapOffValue()
    {
        return FileUtils.byte2Int(mapOff);
    }

    public int getStringIdsSizeValue()
    {
        return FileUtils.byte2Int(stringIdsSize);
    }

    public int getStringIdsOffValue()
    {
        return FileUtils.byte2Int(stringIdsOff);
    }

    public int getTypeIdsSizeValue()
    {
        return FileUtils.byte2Int(typeIdsSize);
    }

    public int getTypeIdsOffValue()
    {
        return FileUtils.byte2Int(typeIdsOff);
    }

    public int getProtoIdsSizeValue()
    {
        return FileUtils.byte2Int(protoIdsSize);
    }

    public int getProtoIdsOffValue()
    {
        return FileUtils.byte2Int(protoIdsOff);
    }

    public int getFieldIdsSizeValue()
    {
        return FileUtils.byte2Int(fieldIdsSize);
    }

    public int getFieldIdsOffValue()
    {
        return FileUtils.byte2Int(fieldIdsOff);
    }

    public int getMethodIdsSizeValue()
    {
        return FileUtils.byte2Int(methodIdsSize);
    }

    public int getMethodIdsOffValue()
    {
        return FileUtils.byte2Int(methodIdsOff);
    }

    public int getClassDefsSizeValue()
    {
        return FileUtils.byte2Int(classDefsSize);
    }

    public int getClassDefsOffValue()
    {
        return FileUtils.byte2Int(classDefsOff);
    }

    public int getDataSizeValue()
    {
        return FileUtils.byte2Int(dataSize);
    }

    public int getDataOffValue()
    {
        return FileUtils.byte2Int(dataOff);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ DexHeader ------------------\n");
        builder.append("Magic: ").append(FileUtils.byte2HexString(magic)).append("\n");
        builder.append("CheckSum: ").append(FileUtils.byte2HexString(checkSum)).append("(").append(Integer.toHexString(getCheckSumValue())).append(")").append("\n");
        builder.append("Signature: ").append(FileUtils.byte2HexString(signature)).append("\n");
        builder.append("FileSize: ").append(FileUtils.byte2HexString(fileSize)).append("(").append(getFileSizeValue()).append(")").append("\n");
        builder.append("HeaderSize: ").append(FileUtils.byte2HexString(headerSize)).append("(").append(getHeaderSizeValue()).append(")").append("\n");
        builder.append("EndianTag: ").append(FileUtils.byte2HexString(endianTag)).append("(").append(Integer.toHexString(getEndianTagValue())).append(")").append("\n");
        builder.append("LinkSize: ").append(FileUtils.byte2HexString(linkSize)).append("(").append(getLinkSizeValue()).append(")").append("\n");
        builder.append("LinkOff: ").append(FileUtils.byte2HexString(linkOff)).append("(").append(getLinkOffValue()).append(")").append("\n");
        builder.append("MapOff: ").append(FileUtils.byte2HexString(mapOff)).append("(").append(getMapOffValue()).append(")").append("\n");
        builder.append("StringIdsSize: ").append(FileUtils.byte2HexString(stringIdsSize)).append("(").append(getStringIdsSizeValue()).append(")").append("\n");
        builder.append("StringIdsOff: ").append(FileUtils.byte2HexString(stringIdsOff)).append("(").append(getStringIdsOffValue()).append(")").append("\n");
        builder.append("TypeIdsSize: ").append(FileUtils.byte2HexString(typeIdsSize)).append("(").append(getTypeIdsSizeValue()).append(")").append("\n");
        builder.append("TypeIdsOff: ").append(FileUtils.byte2HexString(typeIdsOff)).append("(").append(getTypeIdsOffValue()).append(")").append("\n");
        builder.append("ProtoIdsSize: ").append(FileUtils.byte2HexString(protoIdsSize)).append("(").append(getProtoIdsSizeValue()).append(")").append("\n");
        builder.append("ProtoIdsOff: ").append(FileUtils.byte2HexString(protoIdsOff)).append("(").append(getProtoIdsOffValue()).append(")").append("\n");
        builder.append("FieldIdsSize: ").append(FileUtils.byte2HexString(fieldIdsSize)).append("(").append(getFieldIdsSizeValue()).append(")").append("\n");
        builder.append("FieldIdsOff: ").append(FileUtils.byte2HexString(fieldIdsOff)).append("(").append(getFieldIdsOffValue()).append(")").append("\n");
        builder.append("MethodIdsSize: ").append(FileUtils.byte2HexString(methodIdsSize)).append("(").append(getMethodIdsSizeValue()).append(")").append("\n");
        builder.append("MethodIdsOff: ").append(FileUtils.byte2HexString(methodIdsOff)).append("(").append(getMethodIdsOffValue()).append(")").append("\n");
        builder.append("ClassDefsSize: ").append(FileUtils.byte2HexString(classDefsSize)).append("(").append(getClassDefsSizeValue()).append(")").append("\n");
        builder.append("ClassDefsOff: ").append(FileUtils.byte2HexString(classDefsOff)).append("(").append(getClassDefsOffValue()).append(")").append("\n");
        builder.append("DataSize: ").append(FileUtils.byte2HexString(dataSize)).append("(").append(getDataSizeValue()).append(")").append("\n");
        builder.append("DataOff: ").append(FileUtils.byte2HexString(dataOff)).append("(").append(getDataOffValue()).append(")");
        return builder.toString();
    }
}
