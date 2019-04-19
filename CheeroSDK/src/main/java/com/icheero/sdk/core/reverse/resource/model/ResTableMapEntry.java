package com.icheero.sdk.core.reverse.resource.model;

import com.icheero.sdk.util.FileUtils;

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
public class ResTableMapEntry
{
    public ResTableEntry entry;
    public ResTableRef parent;
    public byte[] count = new byte[4];

    public ResTableMapEntry()
    {
        parent = new ResTableRef();
    }

    public int getCountValue()
    {
        return FileUtils.byte2Int(count);
    }

    public static int getLength()
    {
        return ResTableEntry.getLength() + ResTableRef.getLength() + 4;
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ ResTableMapEntry ------------------\n");
        builder.append("Entry: ").append(entry.toString()).append("\n");
        builder.append("Parent: ").append(parent.toString()).append("\n");
        builder.append("Count: ").append(FileUtils.byte2HexString(count)).append("(").append(getCountValue()).append(")").append("\n");
        return builder.toString();
    }
}
