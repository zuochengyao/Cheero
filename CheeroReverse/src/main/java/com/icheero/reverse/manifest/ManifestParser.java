package com.icheero.reverse.manifest;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;
import java.util.List;

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
        parseMagicNumber();
        parseSize();
    }

    private void parseMagicNumber()
    {
        mManifest.magic = Common.copyBytes(mManifestData, MANIFEST_OFFSET_HEADER, 4);
        Log.i(TAG, "Magic Number: " + Common.byte2HexString(mManifest.magic));
    }

    private void parseSize()
    {
        mManifest.size = Common.copyBytes(mManifestData, 4, 4);
        Log.i(TAG, "Manifest Size: " + Common.byte2HexString(mManifest.size));
    }

    // endregion

    // region StringChunk

    public void parseStringChunk()
    {
        parseScSignature();
        int scSizeValue = parseScSize();
        int scStringCountValue = parseScStringCount();
        parseScStyleCount();
        parseScUnknown();
        int scStringPoolOffsetValue = parseScStringPoolOffset();
        parseScStylePoolOffset();
        parseScStringPoolContent(scSizeValue, scStringCountValue, scStringPoolOffsetValue);
        mManifestOffsetResourceChunk = MANIFEST_OFFSET_STRING_CHUNK + scSizeValue;
    }

    private void parseScStringPoolContent(int scSizeValue, int scStringCountValue, int scStringPoolOffsetValue)
    {
        byte[] scStringPoolContent = Common.copyBytes(mManifestData, scStringPoolOffsetValue + MANIFEST_OFFSET_STRING_CHUNK, scSizeValue);

        //这里的格式是：偏移值开始的两个字节是字符串的长度，接着是字符串的内容，后面跟着两个字符串的结束符00
        byte[] firstStringSize = Common.copyBytes(scStringPoolContent, 0, 2);
        short firstStringSizeValue = (short) (Common.byte2Short(firstStringSize) * 2); // 一个字符对应两个字节
        Log.i(TAG, "First String Size: " + Common.byte2HexString(firstStringSize) + "(" + firstStringSizeValue + ")");

        List<String> stringContentList = new ArrayList<>(scStringCountValue);

        byte[] firstStringContentByte = Common.copyBytes(scStringPoolContent, 2, firstStringSizeValue + 2);
        String firstStringContent = filterStringNull(new String(firstStringContentByte));
        stringContentList.add((firstStringContent));
        Log.i(TAG, "first string:" + firstStringContent);

        //将字符串都放到ArrayList中
        int endStringIndex = 2 + firstStringSizeValue + 2;
        while (stringContentList.size() < scStringCountValue)
        {
            //一个字符对应两个字节，所以要乘以2
            int stringSize = Common.byte2Short(Common.copyBytes(scStringPoolContent, endStringIndex, 2)) * 2;
            String str = new String(Common.copyBytes(scStringPoolContent, endStringIndex + 2, stringSize + 2));
            Log.i(TAG, "str:" + filterStringNull(str));
            stringContentList.add(filterStringNull(str));
            endStringIndex += (2 + stringSize + 2);
        }
    }

    private void parseScStylePoolOffset()
    {
        byte[] scStylePoolOffset = Common.copyBytes(mManifestData, 32, 4);
        int scStylePoolOffsetValue = Common.byte2Int(scStylePoolOffset);
        Log.i(TAG, "StylePool Offset: " + Common.byte2HexString(scStylePoolOffset) + "(" + scStylePoolOffsetValue + ")");
    }

    private int parseScStringPoolOffset()
    {
        byte[] scStringPoolOffset = Common.copyBytes(mManifestData, 28, 4);
        int scStringPoolOffsetValue = Common.byte2Int(scStringPoolOffset);
        Log.i(TAG, "StringPool Offset: " + Common.byte2HexString(scStringPoolOffset) + "(" + scStringPoolOffsetValue + ")");
        return scStringPoolOffsetValue;
    }

    private void parseScUnknown()
    {
        byte[] scUnknown = Common.copyBytes(mManifestData, 24, 4);
        Log.i(TAG, "Unknown: " + Common.byte2HexString(scUnknown));
    }

    private void parseScStyleCount()
    {
        byte[] scStyleCount = Common.copyBytes(mManifestData, 20, 4);
        int scStyleCountValue = Common.byte2Int(scStyleCount);
        Log.i(TAG, "Style Count: " + Common.byte2HexString(scStyleCount) + "(" + scStyleCountValue + ")");
    }

    private int parseScStringCount()
    {
        byte[] scStringCount = Common.copyBytes(mManifestData, 16, 4);
        int scStringCountValue = Common.byte2Int(scStringCount);
        Log.i(TAG, "String Count: " + Common.byte2HexString(scStringCount) + "(" + scStringCountValue + ")");
        return scStringCountValue;
    }

    private int parseScSize()
    {
        byte[] scSize = Common.copyBytes(mManifestData, 12, 4);
        int scSizeValue = Common.byte2Int(scSize);
        Log.i(TAG, "scSize: " + Common.byte2HexString(scSize) + "(" + scSizeValue + ")");
        return scSizeValue;
    }

    private void parseScSignature()
    {
        byte[] scSignature = Common.copyBytes(mManifestData, MANIFEST_OFFSET_STRING_CHUNK, 4);
        int scSignatureValue = Common.byte2Int(scSignature);
        Log.i(TAG, "scSignature: " + Common.byte2HexString(scSignature) + "(" + scSignatureValue + ")");
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
