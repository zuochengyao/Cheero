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
    private int mResourceChunkOffset;
    private int mStartNamespaceChunkOffset;

    public ManifestParser(byte[] manifestData)
    {
        this.mManifestData = manifestData;
        mManifest = new Manifest();
    }

    // region Header

    public void parseHeader()
    {
        mManifest.getHeader().hMagic = Common.copyBytes(mManifestData, MANIFEST_OFFSET_HEADER, 4);
        mManifest.getHeader().hSize = Common.copyBytes(mManifestData, 4, 4);
        Log.i(TAG, mManifest.getHeader().toString().split("\n"));
    }
    // endregion

    // region StringChunk

    public void parseStringChunk()
    {
        mManifest.getStringChunk().scSignature = Common.copyBytes(mManifestData, MANIFEST_OFFSET_STRING_CHUNK, 4);
        mManifest.getStringChunk().scSize = Common.copyBytes(mManifestData, 12, 4);
        mManifest.getStringChunk().scStringCount = Common.copyBytes(mManifestData, 16, 4);
        mManifest.getStringChunk().scStyleCount = Common.copyBytes(mManifestData, 20, 4);
        mManifest.getStringChunk().scUnknown = Common.copyBytes(mManifestData, 24, 4);
        mManifest.getStringChunk().scStringPoolOffset = Common.copyBytes(mManifestData, 28, 4);
        mManifest.getStringChunk().scStylePoolOffset = Common.copyBytes(mManifestData, 32, 4);
        parseScStringPoolContent();
        Log.i(TAG, mManifest.getStringChunk().toString().split("\n"));
        mResourceChunkOffset = MANIFEST_OFFSET_STRING_CHUNK + mManifest.getStringChunk().getSizeValue();
    }

    private void parseScStringPoolContent()
    {
        int scSizeValue = mManifest.getStringChunk().getSizeValue();
        int scStringCountValue = mManifest.getStringChunk().getStringCountValue();
        int scStringPoolOffsetValue = mManifest.getStringChunk().getStringPoolOffsetValue();
        mManifest.getStringChunk().scStringPoolContent = Common.copyBytes(mManifestData, scStringPoolOffsetValue + MANIFEST_OFFSET_STRING_CHUNK, scSizeValue);
        mManifest.getStringChunk().scStringPoolContentList = new ArrayList<>(scStringCountValue);
        int endStringIndex = 0;
        for (int i = 0; i < scStringCountValue; i++)
        {
            // 这里的格式是：偏移值开始的两个字节是字符串的长度，接着是字符串的内容，后面跟着两个字符串的结束符00
            byte[] stringSize = Common.copyBytes(mManifest.getStringChunk().scStringPoolContent, endStringIndex, 2);
            // 一个字符对应两个字节，所以要乘以2
            int stringSizeValue = Common.byte2Short(stringSize) * 2;
            String str = new String(Common.copyBytes(mManifest.getStringChunk().scStringPoolContent, endStringIndex + 2, stringSizeValue + 2));
            // 将字符串都放到ArrayList中
            mManifest.getStringChunk().scStringPoolContentList.add(filterStringNull(str));
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

    // region ResourceIdChunk

    public void parseResourceIdChunk()
    {
        mManifest.getResourceIdChunk().rcSignature = Common.copyBytes(mManifestData, mResourceChunkOffset, 4);
        mManifest.getResourceIdChunk().rcSize = Common.copyBytes(mManifestData, mResourceChunkOffset + 4, 4);
        parseResourceIdList();
        Log.i(TAG, mManifest.getResourceIdChunk().toString().split("\n"));
    }

    private void parseResourceIdList()
    {
        // chunkSize的值 包含了chunkTag和chunkSize，所以要去除这8个字节
        int resIdSize = mManifest.getResourceIdChunk().getSizeValue();
        mManifest.getResourceIdChunk().rcResourceId = Common.copyBytes(mManifestData, mResourceChunkOffset + 8, resIdSize - 8);
        mManifest.getResourceIdChunk().rcResourceIdList = new ArrayList<>(mManifest.getResourceIdChunk().rcResourceId.length / 4);
        for (int i = 0; i < mManifest.getResourceIdChunk().rcResourceId.length / 4; i++)
        {
            byte[] resId = Common.copyBytes(mManifest.getResourceIdChunk().rcResourceId, i * 4, 4);
            int resIdValue = Common.byte2Int(resId);
            mManifest.getResourceIdChunk().rcResourceIdList.add(resIdValue);
        }
        mStartNamespaceChunkOffset = mResourceChunkOffset + resIdSize;
    }

    // endregion

    // region StartNamespaceChunk

    public void parseStartNamespaceChunk()
    {
        mManifest.getStartNamespaceChunk().sncSignature = Common.copyBytes(mManifestData, mStartNamespaceChunkOffset, 4);
        mManifest.getStartNamespaceChunk().sncSize = Common.copyBytes(mManifestData, mStartNamespaceChunkOffset + 4, 4);
        mManifest.getStartNamespaceChunk().sncLineNumber = Common.copyBytes(mManifestData, mStartNamespaceChunkOffset + 8, 4);
        mManifest.getStartNamespaceChunk().sncUnknown = Common.copyBytes(mManifestData, mStartNamespaceChunkOffset + 12, 4);
        mManifest.getStartNamespaceChunk().sncPrefix = Common.copyBytes(mManifestData, mStartNamespaceChunkOffset + 16, 4);
        mManifest.getStartNamespaceChunk().sncUri = Common.copyBytes(mManifestData, mStartNamespaceChunkOffset + 20, 4);
        Log.i(TAG, mManifest.getStartNamespaceChunk().toString().split("\n"));
    }

    // endregion
}
