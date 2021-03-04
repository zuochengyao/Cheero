package com.icheero.sdk.core.reverse.resource.model;


import com.icheero.sdk.util.IOUtils;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-02 15:29:32
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * This is a reference to a unique entry (a ResTable_entry structure)
 * in a resource table.  The value is structured as: 0xpptteeee,
 * where pp is the package index, tt is the type index in that
 * package, and eeee is the entry index in that type.  The package
 * and type values start at 1 for the first item, to help catch cases
 * where they have not been supplied.
 *
 * struct ResTable_ref
 * {
 *     uint32_t ident;
 * };
 */
public class ResTableRef
{
    public byte[] ident = new byte[4];

    public static int getLength()
    {
        return 4;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Ident: " + IOUtils.byte2HexString(ident) + "(" + IOUtils.byte2Int(ident) + ")";
    }
}
