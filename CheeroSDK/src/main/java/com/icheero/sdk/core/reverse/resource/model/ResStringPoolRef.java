package com.icheero.sdk.core.reverse.resource.model;

import com.icheero.sdk.core.reverse.resource.ResourceParser;
import com.icheero.sdk.util.FileUtils;

/**
 * @author zcy 2019-04-02 15:23:16
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * Reference to a string in a string pool.
 *
 * struct ResStringPool_ref
 * {
 *     // Index into the string pool table (uint32_t-offset from the indices
 *     // immediately after ResStringPool_header) at which to find the location
 *     // of the string data in the pool.
 *     uint32_t index;
 * };
 */
public class ResStringPoolRef
{
    public byte[] index = new byte[4];

    public static int getLength()
    {
        return 4;
    }

    int getIndexValue()
    {
        return FileUtils.byte2Int(index);
    }

    @Override
    public String toString()
    {
        return "index:" + getIndexValue() + ", str:" + ResourceParser.getKeyString(getIndexValue());
    }

}
