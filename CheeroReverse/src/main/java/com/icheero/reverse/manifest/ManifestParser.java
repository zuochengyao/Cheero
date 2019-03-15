package com.icheero.reverse.manifest;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ManifestParser
{
    private static final Class TAG = ManifestParser.class;

    private byte[] mManifestData;
    private Manifest mManifest;

    public ManifestParser(byte[] manifestData)
    {
        this.mManifestData = manifestData;
        mManifest = new Manifest();
    }

    public void parseMagicNumber()
    {
        mManifest.magic = Common.copyBytes(mManifestData, 0, 4);
        Log.i(TAG, "Magic Number: " + Common.byte2HexString(mManifest.magic));
    }

    public void parseSize()
    {
        mManifest.size = Common.copyBytes(mManifestData, 4, 4);
        Log.i(TAG, "Manifest Size: " + Common.byte2HexString(mManifest.size));
    }

    public void parseStringChunk()
    {
        byte[] chunkType = Common.copyBytes(mManifestData, 8, 4);
        Log.i(TAG, "String Chunk: " + Common.byte2HexString(chunkType));

        byte[] chunkSize = Common.copyBytes(mManifestData, 12, 4);
        int chunkSizeValue =  Common.byte2Int(chunkSize);
        Log.i(TAG, "String Size: " + chunkSizeValue);

        byte[] chunkStringCount = Common.copyBytes(mManifestData, 16, 4);
        int chunkStringCountValue = Common.byte2Int(chunkStringCount);
        Log.i(TAG,"String Count: "+ chunkStringCountValue);

        byte[] chunkStyleCount = Common.copyBytes(mManifestData, 20, 4);
        Log.i(TAG,"Style Count: "+ Common.byte2Int(chunkStyleCount));

        byte[] chunkUnknown = Common.copyBytes(mManifestData, 24, 4);
        Log.i(TAG,"Unknown: "+ Common.byte2HexString(chunkUnknown));

        List<String> stringContentList = new ArrayList<>(chunkStringCountValue);

        byte[] stringOffset = Common.copyBytes(mManifestData, 28, 4);
        int stringOffsetValue = Common.byte2Int(stringOffset) + 8;
        Log.i(TAG, "String offset: " + stringOffsetValue);

        byte[] stringContent = Common.copyBytes(mManifestData, stringOffsetValue, chunkSizeValue);

        //这里的格式是：偏移值开始的两个字节是字符串的长度，接着是字符串的内容，后面跟着两个字符串的结束符00
        byte[] firstStringSize = Common.copyBytes(stringContent, 0, 2);
        int firstStringSizeValue = Common.byte2Int(firstStringSize);
    }
}
