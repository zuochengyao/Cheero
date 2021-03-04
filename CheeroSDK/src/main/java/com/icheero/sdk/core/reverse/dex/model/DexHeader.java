package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.util.IOUtils;

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
        return IOUtils.byte2Int(checkSum);
    }

    public int getFileSizeValue()
    {
        return IOUtils.byte2Int(fileSize);
    }

    public int getHeaderSizeValue()
    {
        return IOUtils.byte2Int(headerSize);
    }

    public int getEndianTagValue()
    {
        return IOUtils.byte2Int(endianTag);
    }

    public int getLinkSizeValue()
    {
        return IOUtils.byte2Int(linkSize);
    }

    public int getLinkOffValue()
    {
        return IOUtils.byte2Int(linkOff);
    }

    public int getMapOffValue()
    {
        return IOUtils.byte2Int(mapOff);
    }

    public int getStringIdsSizeValue()
    {
        return IOUtils.byte2Int(stringIdsSize);
    }

    public int getStringIdsOffValue()
    {
        return IOUtils.byte2Int(stringIdsOff);
    }

    public int getTypeIdsSizeValue()
    {
        return IOUtils.byte2Int(typeIdsSize);
    }

    public int getTypeIdsOffValue()
    {
        return IOUtils.byte2Int(typeIdsOff);
    }

    public int getProtoIdsSizeValue()
    {
        return IOUtils.byte2Int(protoIdsSize);
    }

    public int getProtoIdsOffValue()
    {
        return IOUtils.byte2Int(protoIdsOff);
    }

    public int getFieldIdsSizeValue()
    {
        return IOUtils.byte2Int(fieldIdsSize);
    }

    public int getFieldIdsOffValue()
    {
        return IOUtils.byte2Int(fieldIdsOff);
    }

    public int getMethodIdsSizeValue()
    {
        return IOUtils.byte2Int(methodIdsSize);
    }

    public int getMethodIdsOffValue()
    {
        return IOUtils.byte2Int(methodIdsOff);
    }

    public int getClassDefsSizeValue()
    {
        return IOUtils.byte2Int(classDefsSize);
    }

    public int getClassDefsOffValue()
    {
        return IOUtils.byte2Int(classDefsOff);
    }

    public int getDataSizeValue()
    {
        return IOUtils.byte2Int(dataSize);
    }

    public int getDataOffValue()
    {
        return IOUtils.byte2Int(dataOff);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ DexHeader ------------------\n");
        builder.append("Magic: ").append(IOUtils.byte2HexString(magic)).append("\n");
        builder.append("CheckSum: ").append(IOUtils.byte2HexString(checkSum)).append("(").append(Integer.toHexString(getCheckSumValue())).append(")").append("\n");
        builder.append("Signature: ").append(IOUtils.byte2HexString(signature)).append("\n");
        builder.append("FileSize: ").append(IOUtils.byte2HexString(fileSize)).append("(").append(getFileSizeValue()).append(")").append("\n");
        builder.append("HeaderSize: ").append(IOUtils.byte2HexString(headerSize)).append("(").append(getHeaderSizeValue()).append(")").append("\n");
        builder.append("EndianTag: ").append(IOUtils.byte2HexString(endianTag)).append("(").append(Integer.toHexString(getEndianTagValue())).append(")").append("\n");
        builder.append("LinkSize: ").append(IOUtils.byte2HexString(linkSize)).append("(").append(getLinkSizeValue()).append(")").append("\n");
        builder.append("LinkOff: ").append(IOUtils.byte2HexString(linkOff)).append("(").append(getLinkOffValue()).append(")").append("\n");
        builder.append("MapOff: ").append(IOUtils.byte2HexString(mapOff)).append("(").append(getMapOffValue()).append(")").append("\n");
        builder.append("StringIdsSize: ").append(IOUtils.byte2HexString(stringIdsSize)).append("(").append(getStringIdsSizeValue()).append(")").append("\n");
        builder.append("StringIdsOff: ").append(IOUtils.byte2HexString(stringIdsOff)).append("(").append(getStringIdsOffValue()).append(")").append("\n");
        builder.append("TypeIdsSize: ").append(IOUtils.byte2HexString(typeIdsSize)).append("(").append(getTypeIdsSizeValue()).append(")").append("\n");
        builder.append("TypeIdsOff: ").append(IOUtils.byte2HexString(typeIdsOff)).append("(").append(getTypeIdsOffValue()).append(")").append("\n");
        builder.append("ProtoIdsSize: ").append(IOUtils.byte2HexString(protoIdsSize)).append("(").append(getProtoIdsSizeValue()).append(")").append("\n");
        builder.append("ProtoIdsOff: ").append(IOUtils.byte2HexString(protoIdsOff)).append("(").append(getProtoIdsOffValue()).append(")").append("\n");
        builder.append("FieldIdsSize: ").append(IOUtils.byte2HexString(fieldIdsSize)).append("(").append(getFieldIdsSizeValue()).append(")").append("\n");
        builder.append("FieldIdsOff: ").append(IOUtils.byte2HexString(fieldIdsOff)).append("(").append(getFieldIdsOffValue()).append(")").append("\n");
        builder.append("MethodIdsSize: ").append(IOUtils.byte2HexString(methodIdsSize)).append("(").append(getMethodIdsSizeValue()).append(")").append("\n");
        builder.append("MethodIdsOff: ").append(IOUtils.byte2HexString(methodIdsOff)).append("(").append(getMethodIdsOffValue()).append(")").append("\n");
        builder.append("ClassDefsSize: ").append(IOUtils.byte2HexString(classDefsSize)).append("(").append(getClassDefsSizeValue()).append(")").append("\n");
        builder.append("ClassDefsOff: ").append(IOUtils.byte2HexString(classDefsOff)).append("(").append(getClassDefsOffValue()).append(")").append("\n");
        builder.append("DataSize: ").append(IOUtils.byte2HexString(dataSize)).append("(").append(getDataSizeValue()).append(")").append("\n");
        builder.append("DataOff: ").append(IOUtils.byte2HexString(dataOff)).append("(").append(getDataOffValue()).append(")");
        return builder.toString();
    }
}
