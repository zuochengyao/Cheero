package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.util.IOUtils;

public class EncodedField
{
    public Uleb128 filedIdxDiff;
    public Uleb128 accessFlags;

    @Override
    public String toString()
    {
        return "filedIdxDiff:" + IOUtils.byte2HexString(filedIdxDiff.asBytes()) + ", accessFlags:" + IOUtils.byte2HexString(accessFlags.asBytes());
    }
}
