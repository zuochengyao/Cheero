package com.icheero.reverse.resource.model;

import com.icheero.sdk.util.Common;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-02 15:20:14
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * Extended form of a ResTable_entry for map entries, defining a parent map
 * resource from which to inherit values.
 *
 * struct ResTable_map_entry : public ResTable_entry
 * {
 *     // Resource identifier of the parent mapping, or 0 if there is none.
 *     // This is always treated as a TYPE_DYNAMIC_REFERENCE.
 *     ResTable_ref parent;
 *     // Number of name/value pairs that follow for FLAG_COMPLEX.
 *     uint32_t count;
 * };
 */
public class ResTableMapEntry extends ResTableEntry
{
    public ResTableRef parent;
    public byte[] count = new byte[4];

    public ResTableMapEntry()
    {
        parent = new ResTableRef();
    }

    public int getCountValue()
    {
        return Common.byte2Int(count);
    }

    @Override
    public int getSize()
    {
        return super.getSize() + parent.getSize() + 4;
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append("Parent: ").append(parent.toString()).append("\n");
        builder.append("Count: ").append(Common.byte2HexString(count)).append("(").append(getCountValue()).append(")").append("\n");
        return builder.toString();
    }
}
