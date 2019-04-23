package com.icheero.sdk.core.reverse.resource;

import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.core.reverse.resource.model.ResChunkHeader;
import com.icheero.sdk.core.reverse.resource.model.ResStringPoolHeader;
import com.icheero.sdk.core.reverse.resource.model.ResTableConfig;
import com.icheero.sdk.core.reverse.resource.model.ResTableEntry;
import com.icheero.sdk.core.reverse.resource.model.ResTableHeader;
import com.icheero.sdk.core.reverse.resource.model.ResTableMap;
import com.icheero.sdk.core.reverse.resource.model.ResTableMapEntry;
import com.icheero.sdk.core.reverse.resource.model.ResTablePackage;
import com.icheero.sdk.core.reverse.resource.model.ResTableRef;
import com.icheero.sdk.core.reverse.resource.model.ResTableType;
import com.icheero.sdk.core.reverse.resource.model.ResTableTypeSpec;
import com.icheero.sdk.core.reverse.resource.model.ResValue;
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
    private static List<String> mStringPoolList = new ArrayList<>();
    private static List<String> mStringTypeList = new ArrayList<>();
    private static List<String> mStringKeyList = new ArrayList<>();

    public ResourceParser(byte[] resourceData)
    {
        this.mResourceData = resourceData;
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
                    parseResTablePackageType(FileUtils.copyBytes(mResourceData, nextChunkOffset, chunkSize));
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
        ResStringPoolHeader resStringPoolHeader = new ResStringPoolHeader(mResChunkHeader);
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
        int nextChunkOffset = mResChunkHeader.getHeaderSizeValue();
        // 解析TypeString
        nextChunkOffset += parseResTableTypeString(packageTypeSrc, resTablePackageHeader.getTypeStringsValue());
        // 解析KeyString
        nextChunkOffset += parseResTableKeyString(packageTypeSrc, resTablePackageHeader.getKeyStringsValue());
        while (nextChunkOffset < packageTypeSrc.length)
        {
            mResChunkHeader = parseResChunkHeader(FileUtils.copyBytes(packageTypeSrc, nextChunkOffset, ResChunkHeader.getHeaderLength()));
            int chunkSize = mResChunkHeader.getSizeValue();
            switch (mResChunkHeader.getTypeValue())
            {
                case ResChunkHeader.RES_TABLE_TYPE_SPEC_TYPE:
                    ResTableTypeSpec resTableTypeSpec = parseResTableTypeSpec(FileUtils.copyBytes(packageTypeSrc, nextChunkOffset, chunkSize));
                    Log.i(TAG, "type_name:" + mStringTypeList.get(resTableTypeSpec.id - 1));
                    Log.i(TAG, resTableTypeSpec.toString().split("\n"));
                    nextChunkOffset += chunkSize;
                    break;
                case ResChunkHeader.RES_TABLE_TYPE_TYPE:
                    ResTableType resTableType = parseResTableType(FileUtils.copyBytes(packageTypeSrc, nextChunkOffset, chunkSize));
                    Log.i(TAG, "type_name:" + mStringTypeList.get(resTableType.id - 1));
                    Log.i(TAG, resTableType.toString().split("\n"));
                    nextChunkOffset += chunkSize;
                    break;
            }
        }
    }

    private ResTablePackage parseResTablePackageHeader(byte[] packageHeaderSrc)
    {
        ResTablePackage header = new ResTablePackage(mResChunkHeader);
        FileUtils.copyBytes(packageHeaderSrc, 8, header.id);
        packId = header.getIdValue();
        FileUtils.copyBytes(packageHeaderSrc, 12, header.name);
        FileUtils.copyBytes(packageHeaderSrc, 268, header.typeStrings);
        FileUtils.copyBytes(packageHeaderSrc, 272, header.lastPublicType);
        FileUtils.copyBytes(packageHeaderSrc, 276, header.keyStrings);
        FileUtils.copyBytes(packageHeaderSrc, 280, header.lastPublicKey);
        return header;
    }

    private int parseResTableTypeString(byte[] packageTypeSrc, int start)
    {
        mResChunkHeader = parseResChunkHeader(FileUtils.copyBytes(packageTypeSrc, start, ResChunkHeader.getHeaderLength()));
        byte[] typeStringSrc = FileUtils.copyBytes(packageTypeSrc, start, mResChunkHeader.getSizeValue());
        mStringTypeList = parseResStringPoolChunk(typeStringSrc);
        return mResChunkHeader.getSizeValue();
    }

    private int parseResTableKeyString(byte[] packageTypeSrc, int start)
    {
        mResChunkHeader = parseResChunkHeader(FileUtils.copyBytes(packageTypeSrc, start, ResChunkHeader.getHeaderLength()));
        byte[] keyStringSrc = FileUtils.copyBytes(packageTypeSrc, start, mResChunkHeader.getSizeValue());
        mStringKeyList = parseResStringPoolChunk(keyStringSrc);
        return mResChunkHeader.getSizeValue();
    }

    /**
     * 解析ResTypeSepc类型描述内容
     * @param typeSpecSrc
     */
    private ResTableTypeSpec parseResTableTypeSpec(byte[] typeSpecSrc)
    {
        ResTableTypeSpec resTableTypeSpec = new ResTableTypeSpec(mResChunkHeader);
        resTableTypeSpec.id = typeSpecSrc[8];
        resTypeId = resTableTypeSpec.getIdValue();
        resTableTypeSpec.res0 = typeSpecSrc[9];
        FileUtils.copyBytes(typeSpecSrc, 10, resTableTypeSpec.res1);
        FileUtils.copyBytes(typeSpecSrc, 12, resTableTypeSpec.entryCount);
        for (int i = 0; i < resTableTypeSpec.getEntryCountValue(); i++)
        {
            int element = FileUtils.byte2Int(FileUtils.copyBytes(typeSpecSrc, resTableTypeSpec.getHeaderLength() + i * 4, 4));
            resTableTypeSpec.configMask.add(element);
        }
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
        resTableType.resConfig = parseResTableConfig(FileUtils.copyBytes(typeSrc, 20, ResTableConfig.getLength()));
        int count = resTableType.getEntryCountValue();
        if (count > 0)
        {
            List<Integer> entryOffsets = parseResOffsets(FileUtils.copyBytes(typeSrc, mResChunkHeader.getHeaderSizeValue(), count * 4), count);
            for (int i = 0; i < count; i++)
            {
                int resId = getResId(i);
                Log.i(TAG, "resId:" + FileUtils.byte2HexString(FileUtils.int2Byte(resId)));
                if (entryOffsets.get(i) < 0)
                    continue;
                // 先判断flags，如果是1，则是ResTableMapEntry，否则为ResTableEntry
                short flags = FileUtils.byte2Short(FileUtils.copyBytes(typeSrc, resTableType.getEntriesStartValue() + 2, 2));
                if (flags == 1)
                {
                    ResTableMapEntry mapEntry = parseResTableMapEntry(typeSrc, entryOffsets.get(i));
                    Log.i(TAG, mapEntry.toString().split("\n"));
                    ResTableMap map = parseResTableMap(typeSrc, entryOffsets.get(i) + ResTableMapEntry.getLength());
                    Log.i(TAG, map.toString());
                }
                else
                {
                    ResTableEntry entry = parseResTableEntry(typeSrc, entryOffsets.get(i));
                    Log.i(TAG, entry.toString().split("\n"));
                    ResValue value = parseResValue(typeSrc, entryOffsets.get(i) + ResTableEntry.getLength());
                    Log.i(TAG, value.toString());
                }
            }
        }
        return resTableType;
    }

    private ResTableEntry parseResTableEntry(byte[] entrySrc, int start)
    {
        ResTableEntry entry = new ResTableEntry();
        FileUtils.copyBytes(entrySrc, start, entry.size);
        FileUtils.copyBytes(entrySrc, start + 2, entry.flags);
        FileUtils.copyBytes(entrySrc, start + 4, entry.key.index);
        return entry;

    }

    private ResValue parseResValue(byte[] valueSrc, int start)
    {
        ResValue value = new ResValue();
        FileUtils.copyBytes(valueSrc, start, value.size);
        value.res0 = valueSrc[start + 2];
        value.dataType = valueSrc[start + 3];
        FileUtils.copyBytes(valueSrc, start + 4, value.data);
        return value;
    }

    private ResTableMapEntry parseResTableMapEntry(byte[] mapEntrySrc, int start)
    {
        ResTableMapEntry mapEntry = new ResTableMapEntry();
        mapEntry.entry = parseResTableEntry(mapEntrySrc, start);
        FileUtils.copyBytes(mapEntrySrc, start + 8, mapEntry.parent.ident);
        FileUtils.copyBytes(mapEntrySrc, start + 12, mapEntry.count);
        return mapEntry;
    }

    private ResTableMap parseResTableMap(byte[] mapSrc, int start)
    {
        ResTableMap map = new ResTableMap();
        FileUtils.copyBytes(mapSrc, start, map.name.ident);
        map.value = parseResValue(mapSrc, start + ResTableRef.getLength());
        return map;
    }

    /**
     * 解析ResTableConfig配置信息
     */
    private ResTableConfig parseResTableConfig(byte[] src)
    {
        ResTableConfig config = new ResTableConfig();

        byte[] sizeByte = FileUtils.copyBytes(src, 0, 4);
        config.size = FileUtils.byte2Int(sizeByte);

        //以下结构是Union
        byte[] mccByte = FileUtils.copyBytes(src, 4, 2);
        config.mcc = FileUtils.byte2Short(mccByte);
        byte[] mncByte = FileUtils.copyBytes(src, 6, 2);
        config.mnc = FileUtils.byte2Short(mncByte);
        byte[] imsiByte = FileUtils.copyBytes(src, 4, 4);
        config.imsi = FileUtils.byte2Int(imsiByte);

        //以下结构是Union
        config.language = FileUtils.copyBytes(src, 8, 2);
        config.country = FileUtils.copyBytes(src, 10, 2);
        byte[] localeByte = FileUtils.copyBytes(src, 8, 4);
        config.locale = FileUtils.byte2Int(localeByte);

        //以下结构是Union
        byte[] orientationByte = FileUtils.copyBytes(src, 12, 1);
        config.orientation = orientationByte[0];
        byte[] touchscreenByte = FileUtils.copyBytes(src, 13, 1);
        config.touchscreen = touchscreenByte[0];
        byte[] densityByte = FileUtils.copyBytes(src, 14, 2);
        config.density = FileUtils.byte2Short(densityByte);
        byte[] screenTypeByte = FileUtils.copyBytes(src, 12, 4);
        config.screenType = FileUtils.byte2Int(screenTypeByte);

        //以下结构是Union
        byte[] keyboardByte = FileUtils.copyBytes(src, 16, 1);
        config.keyboard = keyboardByte[0];
        byte[] navigationByte = FileUtils.copyBytes(src, 17, 1);
        config.navigation = navigationByte[0];
        byte[] inputFlagsByte = FileUtils.copyBytes(src, 18, 1);
        config.inputFlags = inputFlagsByte[0];
        byte[] inputPad0Byte = FileUtils.copyBytes(src, 19, 1);
        config.inputPad0 = inputPad0Byte[0];
        byte[] inputByte = FileUtils.copyBytes(src, 16, 4);
        config.input = FileUtils.byte2Int(inputByte);

        //以下结构是Union
        byte[] screenWidthByte = FileUtils.copyBytes(src, 20, 2);
        config.screenWidth = FileUtils.byte2Short(screenWidthByte);
        byte[] screenHeightByte = FileUtils.copyBytes(src, 22, 2);
        config.screenHeight = FileUtils.byte2Short(screenHeightByte);
        byte[] screenSizeByte = FileUtils.copyBytes(src, 20, 4);
        config.screenSize = FileUtils.byte2Int(screenSizeByte);

        //以下结构是Union
        byte[] sdVersionByte = FileUtils.copyBytes(src, 24, 2);
        config.sdVersion = FileUtils.byte2Short(sdVersionByte);
        byte[] minorVersionByte = FileUtils.copyBytes(src, 26, 2);
        config.minorVersion = FileUtils.byte2Short(minorVersionByte);
        byte[] versionByte = FileUtils.copyBytes(src, 24, 4);
        config.version = FileUtils.byte2Int(versionByte);

        //以下结构是Union
        byte[] screenLayoutByte = FileUtils.copyBytes(src, 28, 1);
        config.screenLayout = screenLayoutByte[0];
        byte[] uiModeByte = FileUtils.copyBytes(src, 29, 1);
        config.uiMode = uiModeByte[0];
        byte[] smallestScreenWidthDpByte = FileUtils.copyBytes(src, 30, 2);
        config.smallestScreenWidthDp = FileUtils.byte2Short(smallestScreenWidthDpByte);
        byte[] screenConfigByte = FileUtils.copyBytes(src, 28, 4);
        config.screenConfig = FileUtils.byte2Int(screenConfigByte);

        //以下结构是Union
        byte[] screenWidthDpByte = FileUtils.copyBytes(src, 32, 2);
        config.screenWidthDp = FileUtils.byte2Short(screenWidthDpByte);
        byte[] screenHeightDpByte = FileUtils.copyBytes(src, 34, 2);
        config.screenHeightDp = FileUtils.byte2Short(screenHeightDpByte);
        byte[] screenSizeDpByte = FileUtils.copyBytes(src, 32, 4);
        config.screenSizeDp = FileUtils.byte2Int(screenSizeDpByte);
        config.localeScript = FileUtils.copyBytes(src, 36, 4);
        config.localeVariant = FileUtils.copyBytes(src, 40, 8);
        return config;
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
        List<Integer> stringOffsets = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
        {
            byte[] offsetByte = FileUtils.copyBytes(resStringPoolOffsets, 4 * i, 4);
            int offsetValue = FileUtils.byte2Int(offsetByte);
            stringOffsets.add(offsetValue);
            Log.i(TAG, "ResOffset[" + i + "]:" + FileUtils.byte2HexString(offsetByte) + "(" + offsetValue + ")");
        }
        return stringOffsets;
    }

    public static String getKeyString(int index)
    {
        if (index >= mStringKeyList.size() || index < 0)
            return "";
        return mStringKeyList.get(index);
    }

    public static String getResString(int index)
    {
        if (index >= mStringTypeList.size() || index < 0)
            return "";
        return mStringTypeList.get(index);
    }

    //资源包的id和类型id
    private int packId;
    private int resTypeId;
    /**
     * 获取资源id
     * 这里高位是packid，中位是restypeid，地位是entryid
     */
    private int getResId(int entryId)
    {
        return (((packId) << 24) | (((resTypeId) & 0xFF) << 16) | (entryId & 0xFFFF));
    }
}
