package com.icheero.sdk.core.reverse.resource;

import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.core.reverse.resource.model.ResChunkHeader;
import com.icheero.sdk.core.reverse.resource.model.ResStringPoolHeader;
import com.icheero.sdk.core.reverse.resource.model.ResTableHeader;
import com.icheero.sdk.core.reverse.resource.model.ResTablePackage;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ResourceParser implements IParser
{
    private static final Class TAG = ResourceParser.class;

    private byte[] mResourceData;
    private List<String> mStringList;
    private List<Integer> mStringOffsets;
    private int nextChunkOffset = 0;

    public ResourceParser(byte[] resourceData)
    {
        this.mResourceData = resourceData;
        mStringList = new ArrayList<>();
        mStringOffsets = new ArrayList<>();
    }

    @Override
    public void parse()
    {
        Log.e(TAG, "Parse resource start!");
        parseResTableHeader();
        parseResStringPoolChunk();
        parseResTablePackage();
        Log.e(TAG, "Parse resource finish!");
    }

    // region 解析Res头
    private void parseResTableHeader()
    {
        ResTableHeader resTableHeader = new ResTableHeader();
        byte[] resTableHeaderSrc = FileUtils.copyBytes(mResourceData, nextChunkOffset, resTableHeader.getHeaderSize());
        resTableHeader.header = parseResChunkHeader(FileUtils.copyBytes(resTableHeaderSrc, 0, 8));
        resTableHeader.packageCount = FileUtils.copyBytes(resTableHeaderSrc, 8, 4);
        Log.i(TAG, resTableHeader.toString().split("\n"));
        nextChunkOffset += resTableHeader.getHeaderSize();
    }
    // endregion

    // region 解析StringPoolType
    private void parseResStringPoolChunk()
    {
        ResStringPoolHeader resStringPoolHeader = parseResStringPoolHeader();
        Log.i(TAG, resStringPoolHeader.toString().split("\n"));
        byte[] stringPoolSrc = FileUtils.copyBytes(mResourceData, nextChunkOffset, resStringPoolHeader.header.getSizeValue());
        // 解析StringPool偏移量
        int stringCount = FileUtils.byte2Int(resStringPoolHeader.stringCount);
        parseResStringPoolOffsets(FileUtils.copyBytes(stringPoolSrc, resStringPoolHeader.getHeaderSize(), stringCount * 4), stringCount);
        // 解析StringPool中字符串内容
        int stringStart = FileUtils.byte2Int(resStringPoolHeader.stringsStart);
        parseResStringPoolStr(FileUtils.copyBytes(stringPoolSrc, stringStart, stringPoolSrc.length - stringStart), stringCount, resStringPoolHeader.getFlagsValueEncode());
        nextChunkOffset += stringPoolSrc.length;
    }

    private ResStringPoolHeader parseResStringPoolHeader()
    {
        ResStringPoolHeader resStringPoolHeader = new ResStringPoolHeader();
        byte[] resStringPoolSrc = FileUtils.copyBytes(mResourceData, nextChunkOffset, resStringPoolHeader.getHeaderSize());
        resStringPoolHeader.header = parseResChunkHeader(FileUtils.copyBytes(resStringPoolSrc, 0, 8));
        resStringPoolHeader.stringCount = FileUtils.copyBytes(resStringPoolSrc, 8, 4);
        resStringPoolHeader.styleCount = FileUtils.copyBytes(resStringPoolSrc, 12, 4);
        resStringPoolHeader.flags = FileUtils.copyBytes(resStringPoolSrc, 16, 4);
        resStringPoolHeader.stringsStart = FileUtils.copyBytes(resStringPoolSrc, 20, 4);
        resStringPoolHeader.stylesStart = FileUtils.copyBytes(resStringPoolSrc, 24, 4);
        return resStringPoolHeader;
    }

    private void parseResStringPoolOffsets(byte[] resStringPoolOffsets, int count)
    {
        for (int i = 0; i < count; i++)
        {
            byte[] offsetByte = FileUtils.copyBytes(resStringPoolOffsets, 4 * i, 4);
            int offsetValue = FileUtils.byte2Int(offsetByte);
            mStringOffsets.add(offsetValue);
            Log.i(TAG, "StringOffset[" + i + "]:" + FileUtils.byte2HexString(offsetByte) + "(" + offsetValue + ")");
        }
    }

    private void parseResStringPoolStr(byte[] resStringPoolStr, int count, String encoding)
    {
        for (int i = 0; i < count; i++)
        {
            byte[] sizeBytes = FileUtils.copyBytes(resStringPoolStr, mStringOffsets.get(i), 2);
            int size = sizeBytes[1] & 0x7F;
            String strValue = "";
            if (size != 0)
            {
                try
                {
                    strValue = new String(FileUtils.copyBytes(resStringPoolStr, mStringOffsets.get(i) + 2, size), encoding);
                }
                catch (UnsupportedEncodingException e)
                {
                    Log.e(TAG, "String encode error:" + e.toString());
                }
            }
            mStringList.add(strValue);
            Log.i(TAG, "StringData[" + i + "]:" + FileUtils.byte2HexString(sizeBytes) + "(" + strValue + ")");
        }
    }
    // endregion

    // region 解析TablePackage

    private void parseResTablePackage()
    {
        ResTablePackage resTablePackageHeader = parseResTablePackageHeader();
        Log.i(TAG, resTablePackageHeader.toString().split("\n"));
        nextChunkOffset += resTablePackageHeader.header.getHeaderSizeValue();
    }

    private ResTablePackage parseResTablePackageHeader()
    {
        ResTablePackage resTablePackage = new ResTablePackage();
        resTablePackage.header = parseResChunkHeader(FileUtils.copyBytes(mResourceData, nextChunkOffset, 8));
        byte[] packageHeaderSrc = FileUtils.copyBytes(mResourceData, nextChunkOffset, resTablePackage.header.getHeaderSizeValue());
        resTablePackage.id = FileUtils.copyBytes(packageHeaderSrc, 8, 4);
        resTablePackage.name = FileUtils.copyBytes(packageHeaderSrc, 12, 128 * 2);
        resTablePackage.typeStrings = FileUtils.copyBytes(packageHeaderSrc, 268, 4);
        resTablePackage.lastPublicType = FileUtils.copyBytes(packageHeaderSrc, 272, 4);
        resTablePackage.keyStrings = FileUtils.copyBytes(packageHeaderSrc, 276, 4);
        resTablePackage.lastPublicKey = FileUtils.copyBytes(packageHeaderSrc, 280, 4);
        return resTablePackage;
    }
    // endregion

    private ResChunkHeader parseResChunkHeader(byte[] resChunkHeaderSrc)
    {
        ResChunkHeader resChunkHeader = new ResChunkHeader();
        resChunkHeader.type = FileUtils.copyBytes(resChunkHeaderSrc, 0, 2);
        resChunkHeader.headerSize = FileUtils.copyBytes(resChunkHeaderSrc, 2, 2);
        resChunkHeader.size = FileUtils.copyBytes(resChunkHeaderSrc, 4, 4);
        return resChunkHeader;
    }
}
