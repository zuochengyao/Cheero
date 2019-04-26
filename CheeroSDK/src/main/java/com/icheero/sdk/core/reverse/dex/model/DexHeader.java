package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

public class DexHeader
{
    /**
     * 这 8 个 字节一般是常量，为了使 .dex 文件能够被识别出来，它必须出现在 .dex 文件的最开头的位置
     * 数组的值可以转换为一个字符串如下：
     * { 0x64 0x65 0x78 0x0a 0x30 0x33 0x35 0x00 } = "dex\n035\0"
     * 中间是一个 ‘\n' 符号，后面 035 是 Dex 文件格式的版本
     */
    public byte[] magic = new byte[8];
    /**
     * 文件校验码
     * 使用 alder32 算法校验文件除去 maigc，checksum 外余下的所有文件区域
     * 用于检查文件错误
     */
    public byte[] checkSum = new byte[4];
    /**
     * 签名
     * 使用 SHA-1 算法 hash 除去 magic, checksum 和 signature 外余下的所有文件区域
     * 用于唯一识别本文件
     */
    public byte[] signature = new byte[20];
    /** Dex 文件的大小 */
    public byte[] fileSize = new byte[4];
    /** header 区域的大小 ，单位 Byte ，一般固定为 0x70 常量 */
    public byte[] headerSize = new byte[4];
    /** 大小端标签，标准 .dex 文件格式为 小端 ，此项一般固定为 0x1234 5678 常量 */
    public byte[] endianTag = new byte[4];
    /** 链接数据的大小  */
    public byte[] linkSize = new byte[4];
    /** 链接数据的偏移  */
    public byte[] linkOff = new byte[4];
    /** map item 的偏移地址，该 item 属于 data 区里的内容，值要大于等于 data_off 的大小 */
    public byte[] mapOff = new byte[4];
    /** dex中 所有的字符串内容 的大小和偏移值 */
    public byte[] stringIdsSize = new byte[4];
    public byte[] stringIdsOff = new byte[4];
    /** dex中 类型数据结构 的大小和偏移值 */
    public byte[] typeIdsSize = new byte[4];
    public byte[] typeIdsOff = new byte[4];
    /** dex中 元数据信息数据结构 的大小和偏移值 */
    public byte[] protoIdsSize = new byte[4];
    public byte[] protoIdsOff = new byte[4];
    /** dex中 字段信息数据结构 的大小和偏移值 */
    public byte[] fieldIdsSize = new byte[4];
    public byte[] fieldIdsOff = new byte[4];
    /** dex中 方法信息数据结构 的大小和偏移值 */
    public byte[] methodIdsSize = new byte[4];
    public byte[] methodIdsOff = new byte[4];
    /** dex中 类信息数据结构 的大小和偏移值 */
    public byte[] classDefsSize = new byte[4];
    public byte[] classDefsOff = new byte[4];
    /** dex中 数据区域的结构信息 的大小和偏移值 */
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
