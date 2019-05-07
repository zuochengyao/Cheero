package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.util.FileUtils;

public class EncodedField
{
    public Uleb128 filedIdxDiff;
    public Uleb128 accessFlags;

    @Override
    public String toString()
    {
        return "filedIdxDiff:" + FileUtils.byte2HexString(filedIdxDiff.asBytes()) + ", accessFlags:" + FileUtils.byte2HexString(accessFlags.asBytes());
    }
}
