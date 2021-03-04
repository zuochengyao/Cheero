package com.icheero.sdk.core.reverse.resource.model;

import com.icheero.sdk.util.IOUtils;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-01 14:38:20
 *
 * 头部信息类
 * Resources.arsc文件格式是由一系列的chunk构成
 * 每一个chunk均包含如下结构的ResChunkHeader用来描述这个chunk的基本信息
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * Header that appears at the front of every data chunk in a resource.
 *
 * struct ResChunk_header
 * {
 *     // Type identifier for this chunk.  The meaning of this value depends
 *     // on the containing chunk.
 *     uint16_t type;
 *
 *     // Size of the chunk header (in bytes).  Adding this value to
 *     // the address of the chunk allows you to find its associated data
 *     // (if any).
 *     uint16_t headerSize;
 *
 *     // Total size of this chunk (in bytes).  This is the chunkSize plus
 *     // the size of any data associated with the chunk.  Adding this value
 *     // to the chunk allows you to completely skip its contents (including
 *     // any child chunks).  If this value is the same as chunkSize, there is
 *     // no data associated with the chunk.
 *     uint32_t size;
 * };
 *
 * enum {
 *     RES_NULL_TYPE               = 0x0000,
 *     RES_STRING_POOL_TYPE        = 0x0001,
 *     RES_TABLE_TYPE              = 0x0002,
 *     RES_XML_TYPE                = 0x0003,
 *
 *     // Chunk types in RES_XML_TYPE
 *     RES_XML_FIRST_CHUNK_TYPE    = 0x0100,
 *     RES_XML_START_NAMESPACE_TYPE= 0x0100,
 *     RES_XML_END_NAMESPACE_TYPE  = 0x0101,
 *     RES_XML_START_ELEMENT_TYPE  = 0x0102,
 *     RES_XML_END_ELEMENT_TYPE    = 0x0103,
 *     RES_XML_CDATA_TYPE          = 0x0104,
 *     RES_XML_LAST_CHUNK_TYPE     = 0x017f,
 *     // This contains a uint32_t array mapping strings in the string
 *     // pool back to resource identifiers.  It is optional.
 *     RES_XML_RESOURCE_MAP_TYPE   = 0x0180,
 *
 *     // Chunk types in RES_TABLE_TYPE
 *     RES_TABLE_PACKAGE_TYPE      = 0x0200,
 *     RES_TABLE_TYPE_TYPE         = 0x0201,
 *     RES_TABLE_TYPE_SPEC_TYPE    = 0x0202,
 *     RES_TABLE_LIBRARY_TYPE      = 0x0203
 * };
 */
@SuppressWarnings("StringBufferReplaceableByString")
public class ResChunkHeader
{
    public final static int RES_NULL_TYPE = 0x0000;
    public final static int RES_STRING_POOL_TYPE = 0x0001;
    public final static int RES_TABLE_TYPE = 0x0002;
    public final static int RES_XML_TYPE = 0x0003;

    // Chunk types in RES_XML_TYPE
    public final static int RES_XML_FIRST_CHUNK_TYPE = 0x0100;
    public final static int RES_XML_START_NAMESPACE_TYPE = 0x0100;
    public final static int RES_XML_END_NAMESPACE_TYPE = 0x0101;
    public final static int RES_XML_START_ELEMENT_TYPE = 0x0102;
    public final static int RES_XML_END_ELEMENT_TYPE = 0x0103;
    public final static int RES_XML_CDATA_TYPE = 0x0104;
    public final static int RES_XML_LAST_CHUNK_TYPE = 0x017f;

    // This contains a uint32_t array mapping strings in the string
    // pool back to resource identifiers.  It is optional.
    public final static int RES_XML_RESOURCE_MAP_TYPE = 0x0180;

    // Chunk types in RES_TABLE_TYPE
    public final static int RES_TABLE_PACKAGE_TYPE = 0x0200;
    public final static int RES_TABLE_TYPE_TYPE = 0x0201;
    public final static int RES_TABLE_TYPE_SPEC_TYPE = 0x0202;
    public final static int RES_TABLE_LIBRARY_TYPE = 0x0203;

    /** 当前chunk的类型 */
    public byte[] type = new byte[2];
    /** 当前chunk的头部大小 */
    public byte[] headerSize = new byte[2];
    /** 当前chunk的大小 */
    public byte[] size = new byte[4];

    public static int getHeaderLength()
    {
        return 8;
    }

    public int getTypeValue()
    {
        return IOUtils.byte2Short(type);
    }

    public int getHeaderSizeValue()
    {
        return IOUtils.byte2Short(headerSize);
    }

    public int getSizeValue()
    {
        return IOUtils.byte2Int(size);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ ResChunkHeader ------------------\n");
        builder.append("Type: ").append(IOUtils.byte2HexString(type)).append("(").append(getTypeValue()).append(")").append("\n");
        builder.append("HeaderSize: ").append(IOUtils.byte2HexString(headerSize)).append("(").append(getHeaderSizeValue()).append(")").append("\n");
        builder.append("Size: ").append(IOUtils.byte2HexString(size)).append("(").append(getSizeValue()).append(")").append("\n");
        return builder.toString();
    }
}
