package com.icheero.sdk.core.reverse.resource;

import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.core.reverse.resource.model.ResChunkHeader;
import com.icheero.sdk.core.reverse.resource.model.ResStringPoolHeader;
import com.icheero.sdk.core.reverse.resource.model.ResTableHeader;
import com.icheero.sdk.core.reverse.resource.model.ResTablePackage;
import com.icheero.sdk.core.reverse.resource.model.ResTableType;
import com.icheero.sdk.core.reverse.resource.model.ResTableTypeSpec;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ResourceParser implements IParser
{
    private static final Class TAG = ResourceParser.class;

    private byte[] mResourceData;
    private ResChunkHeader mResChunkHeader;
    private List<String> mStringPoolList;
    private List<String> mStringTypeList;
    private List<String> mStringKeyList;

    public ResourceParser(byte[] resourceData)
    {
        this.mResourceData = resourceData;
        this.mStringPoolList = new ArrayList<>();
        this.mStringTypeList = new ArrayList<>();
        this.mStringKeyList = new ArrayList<>();
    }

    @Override
    public void parse()
    {
        int nextChunkOffset = 0;
        Log.e(TAG, "Parse resource start!");
        while (nextChunkOffset < mResourceData.length)
        {
            mResChunkHeader = parseResChunkHeader(FileUtils.copyBytes(mResourceData, nextChunkOffset, ResChunkHeader.getHeaderLength()));
            int headerSize = mResChunkHeader.getHeaderSizeValue();
            int chunkSize = mResChunkHeader.getSizeValue();
            switch (mResChunkHeader.getTypeValue())
            {
                case ResChunkHeader.RES_TABLE_TYPE:
                    ResTableHeader resTableHeader = parseResTableHeader(FileUtils.copyBytes(mResourceData, nextChunkOffset, headerSize));
                    Log.i(TAG, resTableHeader.toString().split("\n"));
                    nextChunkOffset += headerSize;
                    break;
                case ResChunkHeader.RES_STRING_POOL_TYPE:
                    mStringPoolList = parseResStringPoolChunk(FileUtils.copyBytes(mResourceData, nextChunkOffset, chunkSize));
                    nextChunkOffset += chunkSize;
                    break;
                case ResChunkHeader.RES_TABLE_PACKAGE_TYPE:
                    parseResTablePackageType(FileUtils.copyBytes(mResourceData, 0, chunkSize));
                    nextChunkOffset += chunkSize;
                    break;
            }
        }
        Log.e(TAG, "Parse resource finish!");
    }

    // region 解析Res头
    private ResTableHeader parseResTableHeader(byte[] resTableHeaderSrc)
    {
        ResTableHeader resTableHeader = new ResTableHeader(mResChunkHeader);
        resTableHeader.packageCount = FileUtils.copyBytes(resTableHeaderSrc, 8, 4);
        return resTableHeader;
    }
    // endregion

    // region 解析StringPoolType
    private List<String> parseResStringPoolChunk(byte[] stringPoolSrc)
    {
        ResStringPoolHeader resStringPoolHeader = parseResStringPoolHeader(FileUtils.copyBytes(stringPoolSrc, 0, ResStringPoolHeader.getHeaderLength()));
        Log.i(TAG, resStringPoolHeader.toString().split("\n"));
        // 解析StringPool偏移量
        int stringCount = resStringPoolHeader.getStringCountValue();
        List<Integer> stringOffsets = parseResOffsets(FileUtils.copyBytes(stringPoolSrc, ResStringPoolHeader.getHeaderLength(), stringCount * 4), stringCount);
        // 解析StringPool中字符串内容
        int stringStart = resStringPoolHeader.getStringsStartValue();
        return parseResStringPoolStr(FileUtils.copyBytes(stringPoolSrc, stringStart, stringPoolSrc.length - stringStart), stringOffsets, resStringPoolHeader.getFlagsValueEncode());
    }

    private ResStringPoolHeader parseResStringPoolHeader(byte[] resStringPoolSrc)
    {
        ResStringPoolHeader resStringPoolHeader = new ResStringPoolHeader();
        resStringPoolHeader.header = parseResChunkHeader(FileUtils.copyBytes(resStringPoolSrc, 0, ResChunkHeader.getHeaderLength()));
        resStringPoolHeader.stringCount = FileUtils.copyBytes(resStringPoolSrc, 8, 4);
        resStringPoolHeader.styleCount = FileUtils.copyBytes(resStringPoolSrc, 12, 4);
        resStringPoolHeader.flags = FileUtils.copyBytes(resStringPoolSrc, 16, 4);
        resStringPoolHeader.stringsStart = FileUtils.copyBytes(resStringPoolSrc, 20, 4);
        resStringPoolHeader.stylesStart = FileUtils.copyBytes(resStringPoolSrc, 24, 4);
        return resStringPoolHeader;
    }

    private List<String> parseResStringPoolStr(byte[] resStringPoolStr, List<Integer> stringOffsets, String encoding)
    {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < stringOffsets.size(); i++)
        {
            byte[] sizeBytes = FileUtils.copyBytes(resStringPoolStr, stringOffsets.get(i), 2);
            int size = sizeBytes[1] & 0x7F;
            String strValue = "";
            if (size != 0)
            {
                try
                {
                    strValue = new String(FileUtils.copyBytes(resStringPoolStr, stringOffsets.get(i) + 2, size), encoding);
                }
                catch (UnsupportedEncodingException e)
                {
                    Log.e(TAG, "String encode error:" + e.toString());
                }
            }
            stringList.add(strValue);
            Log.i(TAG, "StringData[" + i + "]:" + FileUtils.byte2HexString(sizeBytes) + "(" + strValue + ")");
        }
        return stringList;
    }
    // endregion

    // region 解析TablePackage

    private void parseResTablePackageType(byte[] packageTypeSrc)
    {
        ResTablePackage resTablePackageHeader = parseResTablePackageHeader(FileUtils.copyBytes(packageTypeSrc, 0, mResChunkHeader.getHeaderSizeValue()));
        Log.i(TAG, resTablePackageHeader.toString().split("\n"));
        int nextChunkOffset = resTablePackageHeader.header.getHeaderSizeValue();
        int count = 0;
        while (nextChunkOffset < packageTypeSrc.length)
        {
            int chunkSize = mResChunkHeader.getSizeValue();
            switch (resTablePackageHeader.header.getTypeValue())
            {
                case ResChunkHeader.RES_STRING_POOL_TYPE:
                    if (count == 0)
                        mStringTypeList = parseResStringPoolChunk(FileUtils.copyBytes(packageTypeSrc, nextChunkOffset, chunkSize));
                    else
                        mStringKeyList = parseResStringPoolChunk(FileUtils.copyBytes(packageTypeSrc, nextChunkOffset, chunkSize));
                    nextChunkOffset += chunkSize;
                    count++;
                    break;
                case ResChunkHeader.RES_TABLE_TYPE_SPEC_TYPE:
                    ResTableTypeSpec resTableTypeSpec = parseResTableTypeSpec(FileUtils.copyBytes(mResourceData, nextChunkOffset, chunkSize));
                    Log.i(TAG, resTableTypeSpec.toString().split("\n"));
                    nextChunkOffset += chunkSize;
                    break;
                case ResChunkHeader.RES_TABLE_TYPE_TYPE:
                    ResTableType resTableType = parseResTableType(FileUtils.copyBytes(mResourceData, nextChunkOffset, chunkSize));
                    Log.i(TAG, resTableType.toString().split("\n"));
                    nextChunkOffset += chunkSize;
                    break;
            }
        }
    }

    private ResTablePackage parseResTablePackageHeader(byte[] packageHeaderSrc)
    {
        ResTablePackage resTablePackage = new ResTablePackage(mResChunkHeader);
        FileUtils.copyBytes(packageHeaderSrc, 8, resTablePackage.id);
        FileUtils.copyBytes(packageHeaderSrc, 12, resTablePackage.name);
        FileUtils.copyBytes(packageHeaderSrc, 268, resTablePackage.typeStrings);
        FileUtils.copyBytes(packageHeaderSrc, 272, resTablePackage.lastPublicType);
        FileUtils.copyBytes(packageHeaderSrc, 276, resTablePackage.keyStrings);
        FileUtils.copyBytes(packageHeaderSrc, 280, resTablePackage.lastPublicKey);
        return resTablePackage;
    }

    private ResTableTypeSpec parseResTableTypeSpec(byte[] typeSpecSrc)
    {
        ResTableTypeSpec resTableTypeSpec = new ResTableTypeSpec(mResChunkHeader);
        resTableTypeSpec.id = typeSpecSrc[8];
        resTableTypeSpec.res0 = typeSpecSrc[9];
        FileUtils.copyBytes(typeSpecSrc, 10, resTableTypeSpec.res1);
        FileUtils.copyBytes(typeSpecSrc, 12, resTableTypeSpec.entryCount);
        return resTableTypeSpec;
    }

    private ResTableType parseResTableType(byte[] typeSrc)
    {
        ResTableType resTableType = new ResTableType(mResChunkHeader);
        resTableType.id = typeSrc[8];
        resTableType.res0 = typeSrc[9];
        FileUtils.copyBytes(typeSrc, 10, resTableType.res1);
        FileUtils.copyBytes(typeSrc, 12, resTableType.entryCount);
        FileUtils.copyBytes(typeSrc, 16, resTableType.entriesStart);
        List<Integer> entryOffsets = parseResOffsets(FileUtils.copyBytes(typeSrc, resTableType.header.getHeaderSizeValue(), resTableType.header.getSizeValue() * 4), resTableType.header.getSizeValue());
        return resTableType;
    }

    // endregion
    private ResChunkHeader parseResChunkHeader(byte[] resChunkHeaderSrc)
    {
        ResChunkHeader resChunkHeader = new ResChunkHeader();
        FileUtils.copyBytes(resChunkHeaderSrc, 0, resChunkHeader.type);
        FileUtils.copyBytes(resChunkHeaderSrc, 2, resChunkHeader.headerSize);
        FileUtils.copyBytes(resChunkHeaderSrc, 4, resChunkHeader.size);
        return resChunkHeader;
    }

    private List<Integer> parseResOffsets(byte[] resStringPoolOffsets, int count)
    {
        List<Integer> stringOffsets = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            byte[] offsetByte = FileUtils.copyBytes(resStringPoolOffsets, 4 * i, 4);
            int offsetValue = FileUtils.byte2Int(offsetByte);
            stringOffsets.add(offsetValue);
            Log.i(TAG, "ResOffset[" + i + "]:" + FileUtils.byte2HexString(offsetByte) + "(" + offsetValue + ")");
        }
        return stringOffsets;
    }
}
