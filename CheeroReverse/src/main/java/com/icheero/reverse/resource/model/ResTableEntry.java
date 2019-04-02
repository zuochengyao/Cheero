package com.icheero.reverse.resource.model;

import com.icheero.sdk.util.Common;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-02 15:06:33
 *
 * 描述一个资源项的具体信息
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * This is the beginning of information about an entry in the resource
 * table.  It holds the reference to the name of this entry, and is
 * immediately followed by one of:
 *   * A Res_value structure, if FLAG_COMPLEX is -not- set.
 *   * An array of ResTable_map structures, if FLAG_COMPLEX is set.
 *     These supply a set of name/value mappings of data.
 *
 * struct ResTable_entry
 * {
 *     // Number of bytes in this structure.
 *     uint16_t size;
 *
 *     enum {
 *         // If set, this is a complex entry, holding a set of name/value
 *         // mappings.  It is followed by an array of ResTable_map structures.
 *         FLAG_COMPLEX = 0x0001,
 *         // If set, this resource has been declared public, so libraries
 *         // are allowed to reference it.
 *         FLAG_PUBLIC = 0x0002,
 *         // If set, this is a weak resource and may be overriden by strong
 *         // resources of the same name/type. This is only useful during
 *         // linking with other resource tables.
 *         FLAG_WEAK = 0x0004
 *     };
 *     uint16_t flags;
 *
 *     // Reference into ResTable_package::keyStrings identifying this entry.
 *     struct ResStringPool_ref key;
 * };
 *
 */
public class ResTableEntry
{
    public final static int FLAG_COMPLEX = 0x0001;
    public final static int FLAG_PUBLIC = 0x0002;
    public final static int FLAG_WEAK = 0x0004;

    public byte[] size = new byte[2];
    public byte[] flags = new byte[2];
    public ResStringPoolRef key;

    public ResTableEntry()
    {
        this.key = new ResStringPoolRef();
    }

    public int getSizeValue()
    {
        return Common.byte2Int(size);
    }

    public int getFlagsValue()
    {
        return Common.byte2Int(flags);
    }

    public int getSize()
    {
        return 2 + 2 + key.getSize();
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ ResTableEntry ------------------\n");
        builder.append("Size: ").append(Common.byte2HexString(size)).append("(").append(getSizeValue()).append(")").append("\n");
        builder.append("flags: ").append(Common.byte2HexString(flags)).append("(").append(getFlagsValue()).append(")").append("\n");
        return builder.toString();
    }
}
