package com.icheero.reverse.resource.model;

import com.icheero.sdk.util.Common;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-02 14:41:49
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * A collection of resource entries for a particular resource data
 * type.
 *
 * If the flag FLAG_SPARSE is not set in `flags`， then this struct is
 * followed by an array of uint32_t defining the resource
 * values， corresponding to the array of type strings in the
 * ResTable_package::typeStrings string block. Each of these hold an
 * index from entriesStart; a value of NO_ENTRY means that entry is
 * not defined.
 *
 * If the flag FLAG_SPARSE is set in `flags`， then this struct is followed
 * by an array of ResTable_sparseTypeEntry defining only the entries that
 * have values for this type. Each entry is sorted by their entry ID such
 * that a binary search can be performed over the entries. The ID and offset
 * are encoded in a uint32_t. See ResTabe_sparseTypeEntry.
 *
 * There may be multiple of these chunks for a particular resource type，
 * supply different configuration variations for the resource values of
 * that type.
 *
 * It would be nice to have an additional ordered index of entries， so
 * we can do a binary search if trying to find a resource by string name.
 *
 * struct ResTable_type
 * {
 *     struct ResChunk_header header;
 *
 *     enum {
 *         NO_ENTRY = 0xFFFFFFFF
 *     };
 *
 *     // The type identifier this chunk is holding.  Type IDs start
 *     // at 1 (corresponding to the value of the type bits in a
 *     // resource identifier).  0 is invalid.
 *     uint8_t id;
 *
 *     enum {
 *         // If set， the entry is sparse， and encodes both the entry ID and offset into each entry，
 *         // and a binary search is used to find the key. Only available on platforms >= O.
 *         // Mark any types that use this with a v26 qualifier to prevent runtime issues on older
 *         // platforms.
 *         FLAG_SPARSE = 0x01，
 *     };
 *     uint8_t flags;
 *
 *     // Must be 0.
 *     uint16_t reserved;
 *
 *     // Number of uint32_t entry indices that follow.
 *     uint32_t entryCount;
 *
 *     // Offset from header where ResTable_entry data starts.
 *     uint32_t entriesStart;
 *
 *     // Configuration this collection of entries is designed for. This must always be last.
 *     ResTable_config config;
 * };
 */
public class ResTableType
{
    public static final int NO_ENTRY = 0xFFFFFFFF;
    public static final int FLAG_SPARSE = 0x01;

    /** Chunk的头部信息结构 */
    public ResChunkHeader header;
    /** 标识资源的Type ID */
    public byte id;
    /** 保留，始终为0 */
    public byte res0;
    /** 保留，始终为0 */
    public byte[] res1 = new byte[2];
    /** 等于本类型的资源项个数，指名称相同的资源项的个数 */
    public byte[] entryCount = new byte[4];
    /** 等于资源项数据块相对头部的偏移值 */
    public byte[] entriesStart = new byte[4];
    /** 指向一个ResTable_config，用来描述配置信息，地区，语言，分辨率等 */
    public ResTableConfig resConfig;

    public ResTableType()
    {
        header = new ResChunkHeader();
        resConfig = new ResTableConfig();
    }

    public byte getIdValue()
    {
        return id;
    }

    public byte getRes0Value()
    {
        return res0;
    }

    public short getRes1Value()
    {
        return Common.byte2Short(res1);
    }

    public int getEntryCountValue()
    {
        return Common.byte2Int(entryCount);
    }

    public int getEntriesStartValue()
    {
        return Common.byte2Int(entriesStart);
    }

    public int getSize()
    {
        return header.getHeaderSize() + 1 + 1 + 2 + 4 + 4;
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ ResTableType ------------------\n");
        builder.append("Header: ").append("\n").append(header.toString()).append("\n");
        builder.append("Id: ").append(Common.byte2HexString(id)).append("(").append(getIdValue()).append(")").append("\n");
        builder.append("res0: ").append(Common.byte2HexString(res0)).append("(").append(getRes0Value()).append(")").append("\n");
        builder.append("res1: ").append(Common.byte2HexString(res1)).append("(").append(getRes1Value()).append(")").append("\n");
        builder.append("EntryCount: ").append(Common.byte2HexString(entryCount)).append("(").append(getEntryCountValue()).append(")").append("\n");
        builder.append("EntriesStart: ").append(Common.byte2HexString(entriesStart)).append("(").append(getEntriesStartValue()).append(")").append("\n");
        return builder.toString();
    }
}
