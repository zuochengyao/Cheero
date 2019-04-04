package com.icheero.sdk.core.reverse.resource.model;

import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-01 15:03:08
 *
 * Resources.arsc文件的第一个结构是资源索引表头部
 * 描述了Resources.arsc文件的大小和资源包数量
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * Header for a resource table.  Its data contains a series of
 * additional chunks:
 *   * A ResStringPool_header containing all table values.  This string pool
 *     contains all of the string values in the entire resource table (not
 *     the names of entries or type identifiers however).
 *   * One or more ResTable_package chunks.
 *
 * Specific entries within a resource table can be uniquely identified
 * with a single integer as defined by the ResTable_ref structure.
 *
 * struct ResTable_header
 * {
 *     struct ResChunk_header header;
 *
 *     // The number of ResTable_package structures.
 *     uint32_t packageCount;
 * };
 */
@SuppressWarnings("StringBufferReplaceableByString")
public class ResTableHeader
{
    /** Chunk头部信息格式 */
    public ResChunkHeader header;
    /**
     * 被编译的资源包的个数
     * Android中一个apk可能包含多个资源包
     * 默认情况下都只有一个就是应用的包名所在的资源包
     */
    public byte[] packageCount = new byte[4];

    int getPackageCountValue()
    {
        return FileUtils.byte2Int(packageCount);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ ResTableHeader ------------------\n");
        builder.append("Header: ").append("\n").append(header.toString()).append("\n");
        builder.append("PackageCount: ").append(FileUtils.byte2HexString(packageCount)).append("(").append(getPackageCountValue()).append(")").append("\n");
        return builder.toString();
    }
}
