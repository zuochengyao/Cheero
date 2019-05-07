package com.icheero.sdk.core.reverse.dex.model;

public class EncodedMethod
{
    public Uleb128 methodIdxDiff;
    public Uleb128 accessFlags;
    public Uleb128 codeOff;

//    @Override
//    public String toString()
//    {
//        return "method_idx_diff:" + FileUtils.byte2HexString(methodIdxDiff.asBytes()) + "," + FileUtils.byte2HexString(FileUtils.int2Byte(FileUtils.decodeUleb128(methodIdxDiff))) +
//                ",access_flags:" + FileUtils.byte2HexString(accessFlags.asBytes()) + "," + FileUtils.byte2HexString(FileUtils.int2Byte(FileUtils.decodeUleb128(accessFlags))) +
//                ",code_off:" + FileUtils.byte2HexString(codeOff.asBytes()) + "," + FileUtils.byte2HexString(codeOff);
//    }
}
