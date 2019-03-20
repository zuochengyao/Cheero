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
    private int mNextChunkOffset;

    public ManifestParser(byte[] manifestData)
    {
        this.mManifestData = manifestData;
        mManifest = new Manifest();
    }

    // region Header

    public void parseHeader()
    {
        mManifest.getHeader().h_magic = Common.copyBytes(mManifestData, MANIFEST_OFFSET_HEADER, 4);
        mManifest.getHeader().h_size = Common.copyBytes(mManifestData, 4, 4);
        Log.i(TAG, mManifest.getHeader().toString().split("\n"));
    }
    // endregion

    // region StringChunk

    public void parseStringChunk()
    {
        mManifest.getStringChunk().sc_signature = Common.copyBytes(mManifestData, MANIFEST_OFFSET_STRING_CHUNK, 4);
        mManifest.getStringChunk().sc_size = Common.copyBytes(mManifestData, 12, 4);
        mManifest.getStringChunk().sc_stringCount = Common.copyBytes(mManifestData, 16, 4);
        mManifest.getStringChunk().sc_styleCount = Common.copyBytes(mManifestData, 20, 4);
        mManifest.getStringChunk().sc_unknown = Common.copyBytes(mManifestData, 24, 4);
        mManifest.getStringChunk().sc_stringPoolOffset = Common.copyBytes(mManifestData, 28, 4);
        mManifest.getStringChunk().sc_stylePoolOffset = Common.copyBytes(mManifestData, 32, 4);
        parseScStringPoolContent();
        Log.i(TAG, mManifest.getStringChunk().toString().split("\n"));
        mResourceChunkOffset = MANIFEST_OFFSET_STRING_CHUNK + mManifest.getStringChunk().getSizeValue();
    }

    private void parseScStringPoolContent()
    {
        int scSizeValue = mManifest.getStringChunk().getSizeValue();
        int scStringCountValue = mManifest.getStringChunk().getStringCountValue();
        int scStringPoolOffsetValue = mManifest.getStringChunk().getStringPoolOffsetValue();
        mManifest.getStringChunk().sc_stringPoolContent = Common.copyBytes(mManifestData, scStringPoolOffsetValue + MANIFEST_OFFSET_STRING_CHUNK, scSizeValue);
        mManifest.getStringChunk().sc_stringPoolContentList = new ArrayList<>(scStringCountValue);
        int endStringIndex = 0;
        for (int i = 0; i < scStringCountValue; i++)
        {
            // 这里的格式是：偏移值开始的两个字节是字符串的长度，接着是字符串的内容，后面跟着两个字符串的结束符00
            byte[] stringSize = Common.copyBytes(mManifest.getStringChunk().sc_stringPoolContent, endStringIndex, 2);
            // 一个字符对应两个字节，所以要乘以2
            int stringSizeValue = Common.byte2Short(stringSize) * 2;
            String str = new String(Common.copyBytes(mManifest.getStringChunk().sc_stringPoolContent, endStringIndex + 2, stringSizeValue + 2));
            // 将字符串都放到ArrayList中
            mManifest.getStringChunk().sc_stringPoolContentList.add(filterStringNull(str));
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
        mManifest.getResourceIdChunk().rc_signature = Common.copyBytes(mManifestData, mResourceChunkOffset, 4);
        mManifest.getResourceIdChunk().rc_size = Common.copyBytes(mManifestData, mResourceChunkOffset + 4, 4);
        parseResourceIdList();
        Log.i(TAG, mManifest.getResourceIdChunk().toString().split("\n"));

    }

    private void parseResourceIdList()
    {
        // chunkSize的值 包含了chunkTag和chunkSize，所以要去除这8个字节
        int resIdSize = mManifest.getResourceIdChunk().getSizeValue();
        mManifest.getResourceIdChunk().rc_resourceId = Common.copyBytes(mManifestData, mResourceChunkOffset + 8, resIdSize - 8);
        mManifest.getResourceIdChunk().rc_resourceIdList = new ArrayList<>( mManifest.getResourceIdChunk().rc_resourceId.length / 4);
        for (int i = 0; i < mManifest.getResourceIdChunk().rc_resourceId.length / 4; i++)
        {
            byte[] resId = Common.copyBytes(mManifest.getResourceIdChunk().rc_resourceId, i * 4, 4);
            int resIdValue = Common.byte2Int(resId);
            mManifest.getResourceIdChunk().rc_resourceIdList.add(resIdValue);
        }
        mNextChunkOffset = mResourceChunkOffset + resIdSize;
    }

    // endregion
}
