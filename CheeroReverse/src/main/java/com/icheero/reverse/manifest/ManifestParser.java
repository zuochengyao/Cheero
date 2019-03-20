package com.icheero.reverse.manifest;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;

public class ManifestParser
{
    private static final Class TAG = ManifestParser.class;

    private static final int MANIFEST_OFFSET_HEADER = 0;
    private static final int MANIFEST_OFFSET_STRING_CHUNK = 8;

    private byte[] mManifestData;
    private Manifest mManifest;
    private int mManifestOffsetResourceChunk;

    public ManifestParser(byte[] manifestData)
    {
        this.mManifestData = manifestData;
        mManifest = new Manifest();
    }

    // region Header

    public void parseHeader()
    {
        mManifest.header.h_magic = Common.copyBytes(mManifestData, MANIFEST_OFFSET_HEADER, 4);
        mManifest.header.h_size = Common.copyBytes(mManifestData, 4, 4);
        Log.i(TAG, mManifest.header.toString().split("\n"));
    }
    // endregion

    // region StringChunk

    public void parseStringChunk()
    {
        mManifest.stringChunk.sc_signature = Common.copyBytes(mManifestData, MANIFEST_OFFSET_STRING_CHUNK, 4);
        mManifest.stringChunk.sc_size = Common.copyBytes(mManifestData, 12, 4);
        mManifest.stringChunk.sc_stringCount = Common.copyBytes(mManifestData, 16, 4);
        mManifest.stringChunk.sc_styleCount = Common.copyBytes(mManifestData, 20, 4);
        mManifest.stringChunk.sc_unknown = Common.copyBytes(mManifestData, 24, 4);
        mManifest.stringChunk.sc_stringPoolOffset = Common.copyBytes(mManifestData, 28, 4);
        mManifest.stringChunk.sc_stylePoolOffset = Common.copyBytes(mManifestData, 32, 4);
        parseScStringPoolContent();
        Log.i(TAG, mManifest.stringChunk.toString().split("\n"));
        mManifestOffsetResourceChunk = MANIFEST_OFFSET_STRING_CHUNK + mManifest.stringChunk.getSizeValue();
    }

    private void parseScStringPoolContent()
    {
        int scSizeValue = mManifest.stringChunk.getSizeValue();
        int scStringCountValue = mManifest.stringChunk.getStringCountValue();
        int scStringPoolOffsetValue = mManifest.stringChunk.getStringPoolOffsetValue();
        mManifest.stringChunk.sc_stringPoolContent = Common.copyBytes(mManifestData, scStringPoolOffsetValue + MANIFEST_OFFSET_STRING_CHUNK, scSizeValue);
        mManifest.stringChunk.sc_stringContentList = new ArrayList<>(scStringCountValue);
        int endStringIndex = 0;
        for (int i = 0; i < scStringCountValue; i++)
        {
            // 这里的格式是：偏移值开始的两个字节是字符串的长度，接着是字符串的内容，后面跟着两个字符串的结束符00
            byte[] stringSize = Common.copyBytes(mManifest.stringChunk.sc_stringPoolContent, endStringIndex, 2);
            // 一个字符对应两个字节，所以要乘以2
            int stringSizeValue = Common.byte2Short(stringSize) * 2;
            String str = new String(Common.copyBytes(mManifest.stringChunk.sc_stringPoolContent, endStringIndex + 2, stringSizeValue + 2));
            // 将字符串都放到ArrayList中
            mManifest.stringChunk.sc_stringContentList.add(filterStringNull(str));
            endStringIndex += (2 + stringSizeValue + 2);
        }
    }

    private String filterStringNull(String str)
    {
        if (str == null || str.length() == 0)
            return str;
        byte[] strBytes = str.getBytes();
        ArrayList<Byte> newByte = new ArrayList<>();

        for (byte strByte : strBytes)
        {
            if (strByte != 0)
                newByte.add(strByte);
        }
        byte[] newByteAry = new byte[newByte.size()];
        for (int i = 0; i < newByteAry.length; i++)
            newByteAry[i] = newByte.get(i);
        return new String(newByteAry);
    }
    // endregion
}
