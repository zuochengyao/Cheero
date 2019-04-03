package com.icheero.sdk.core.reverse.resource.model;

import com.icheero.sdk.util.Common;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-02 14:34:59
 *
 * 类型规范数据块：用来描述资源项的配置差异性
 *
 * 通过这个差异性描述，我们就可以知道每一个资源项的配置状况
 * 知道了一个资源项的配置状况之后，Android资源管理框架在检测到设备的配置信息发生变化之后，就可以知道是否需要重新加载该资源项
 * 类型规范数据块是按照类型来组织的，也就是说，每一种类型都对应有一个类型规范数据块
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * A specification of the resources defined by a particular type.
 *
 * There should be one of these chunks for each resource type.
 *
 * This structure is followed by an array of integers providing the set of
 * configuration change flags (ResTable_config::CONFIG_*) that have multiple
 * resources for that configuration.  In addition, the high bit is set if that
 * resource has been made public.
 *
 *
 * struct ResTable_typeSpec
 * {
 *     struct ResChunk_header header;
 *
 *     // The type identifier this chunk is holding.  Type IDs start
 *     // at 1 (corresponding to the value of the type bits in a
 *     // resource identifier).  0 is invalid.
 *     uint8_t id;
 *
 *     // Must be 0.
 *     uint8_t res0;
 *     // Must be 0.
 *     uint16_t res1;
 *
 *     // Number of uint32_t entry configuration masks that follow.
 *     uint32_t entryCount;
 *
 *     enum : uint32_t {
 *         // Additional flag indicating an entry is public.
 *         SPEC_PUBLIC = 0x40000000u,
 *
 *         // Additional flag indicating an entry is overlayable at runtime.
 *         // Added in Android-P.
 *         SPEC_OVERLAYABLE = 0x80000000u,
 *     };
 * };
 */
public class ResTableTypeSpec
{
    public static final int SPEC_PUBLIC = 0x40000000;
    public static final int SPEC_OVERLAYABLE = 0x80000000;

    /** Chunk的头部信息结构 */
    public ResChunkHeader header;
    /**
     * 标识资源的TypeID：指资源的类型ID
     *
     * 资源的类型有animator、anim、color、drawable、layout、menu、raw、string和xml等等若干种，每一种都会被赋予一个ID。
     */
    public byte id;
    /** 保留,始终为0 */
    public byte res0;
    /** 保留,始终为0 */
    public byte[] res1 = new byte[2];
    /** 等于本类型的资源项个数,指名称相同的资源项的个数 */
    public byte[] entryCount = new byte[4];

    public ResTableTypeSpec()
    {
        header = new ResChunkHeader();
    }

    public ResChunkHeader getHeader()
    {
        return header;
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

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ ResTableTypeSpec ------------------\n");
        builder.append("Header: ").append("\n").append(header.toString()).append("\n");
        builder.append("Id: ").append(Common.byte2HexString(id)).append("(").append(getIdValue()).append(")").append("\n");
        builder.append("res0: ").append(Common.byte2HexString(res0)).append("(").append(getRes0Value()).append(")").append("\n");
        builder.append("res1: ").append(Common.byte2HexString(res1)).append("(").append(getRes1Value()).append(")").append("\n");
        builder.append("EntryCount: ").append(Common.byte2HexString(entryCount)).append("(").append(getEntryCountValue()).append(")").append("\n");
        return builder.toString();
    }
}
