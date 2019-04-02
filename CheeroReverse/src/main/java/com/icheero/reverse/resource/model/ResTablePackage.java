package com.icheero.reverse.resource.model;

import com.icheero.sdk.util.Common;

import java.util.Arrays;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-02 11:54:18
 *
 * Package数据块
 * 记录编译包的元数据
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * A collection of resource data types within a package.  Followed by
 * one or more ResTable_type and ResTable_typeSpec structures containing the
 * entry values for each resource type.
 *
 * struct ResTable_package
 * {
 *     struct ResChunk_header header;
 *
 *     // If this is a base package, its ID.  Package IDs start
 *     // at 1 (corresponding to the value of the package bits in a
 *     // resource identifier).  0 means this is not a base package.
 *     uint32_t id;
 *
 *     // Actual name of this package, \0-terminated.
 *     uint16_t name[128];
 *
 *     // Offset to a ResStringPool_header defining the resource
 *     // type symbol table.  If zero, this package is inheriting from
 *     // another base package (overriding specific values in it).
 *     uint32_t typeStrings;
 *
 *     // Last index into typeStrings that is for public use by others.
 *     uint32_t lastPublicType;
 *
 *     // Offset to a ResStringPool_header defining the resource
 *     // key symbol table.  If zero, this package is inheriting from
 *     // another base package (overriding specific values in it).
 *     uint32_t keyStrings;
 *
 *     // Last index into keyStrings that is for public use by others.
 *     uint32_t lastPublicKey;
 *
 *     uint32_t typeIdOffset;
 * };
 */
public class ResTablePackage
{
    /** Chunk的头部信息数据结构 */
    public ResChunkHeader header;
    /**
     * 包的ID：等于PackageId
     * 一般用户包的值PackageId为0X7F，系统资源包的Package Id为0X01
     */
    public byte[] id = new byte[4];
    /** 包名 */
    public byte[] name = new byte[128 * 2];
    /** 类型字符串资源池相对头部的偏移 */
    public byte[] typeStrings = new byte[4];
    /** 最后一个导出的Public类型字符串在类型字符串资源池中的索引，目前这个值设置为类型字符串资源池的元素个数 */
    public byte[] lastPublicType = new byte[4];
    /** 资源项名称字符串相对头部的偏移 */
    public byte[] keyStrings = new byte[4];
    /** 最后一个导出的Public资源项名称字符串在资源项名称字符串资源池中的索引，目前这个值设置为资源项名称字符串资源池的元素个数 */
    public byte[] lastPublicKey = new byte[4];

    public ResTablePackage()
    {
        header = new ResChunkHeader();
    }

    public int getIdValue()
    {
        return Common.byte2Int(id);
    }

    public String getNameStr()
    {
        return Arrays.toString(Common.byte2Char(name));
    }

    public int getTypeStringsValue()
    {
        return Common.byte2Int(typeStrings);
    }

    public int getLastPublicTypeValue()
    {
        return Common.byte2Int(lastPublicType);
    }

    public int getKeyStringsValue()
    {
        return Common.byte2Int(keyStrings);
    }

    public int getLastPublicKeyValue()
    {
        return Common.byte2Int(lastPublicKey);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ ResTablePackage ------------------\n");
        builder.append("Header: ").append("\n").append(header.toString()).append("\n");
        builder.append("Id: ").append(Common.byte2HexString(id)).append("(").append(getIdValue()).append(")").append("\n");
        builder.append("Name: ").append(Common.byte2HexString(name)).append("(").append(getNameStr()).append(")").append("\n");
        builder.append("TypeStrings: ").append(Common.byte2HexString(typeStrings)).append("(").append(getTypeStringsValue()).append(")").append("\n");
        builder.append("LastPublicType: ").append(Common.byte2HexString(lastPublicType)).append("(").append(getLastPublicTypeValue()).append(")").append("\n");
        builder.append("KeyStrings: ").append(Common.byte2HexString(keyStrings)).append("(").append(getKeyStringsValue()).append(")").append("\n");
        builder.append("LastPublicKey: ").append(Common.byte2HexString(lastPublicKey)).append("(").append(getLastPublicKeyValue()).append(")").append("\n");
        return builder.toString();
    }
}
