package com.icheero.sdk.core.reverse.dex;

import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.core.reverse.dex.model.Dex;
import com.icheero.sdk.core.reverse.dex.model.DexHeader;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

public class DexParser implements IParser
{
    private static final Class TAG = DexParser.class;

    private byte[] mDexData;
    private Dex mDex;
    private DexHeader mDexHeader;

    public DexParser(byte[] dexData)
    {
        this.mDexData = dexData;
        mDexHeader = new DexHeader();
        mDex = new Dex(mDexHeader);
    }

    @Override
    public void parse()
    {
        Log.e(TAG, "Parse dex start!");
        parseDexHeader(mDexData);
        Log.i(TAG, mDexHeader.toString().split("\n"));
        Log.e(TAG, "Parse dex finish!");
    }

    private void parseDexHeader(byte[] headerSrc)
    {
        FileUtils.copyBytes(headerSrc, 0, mDexHeader.magic);
        FileUtils.copyBytes(headerSrc, 8, mDexHeader.checkSum);
        FileUtils.copyBytes(headerSrc, 12, mDexHeader.signature);
        FileUtils.copyBytes(headerSrc, 32, mDexHeader.fileSize);
        FileUtils.copyBytes(headerSrc, 36, mDexHeader.headerSize);
        FileUtils.copyBytes(headerSrc, 40, mDexHeader.endianTag);
        FileUtils.copyBytes(headerSrc, 44, mDexHeader.linkSize);
        FileUtils.copyBytes(headerSrc, 48, mDexHeader.linkOff);
        FileUtils.copyBytes(headerSrc, 52, mDexHeader.mapOff);
        FileUtils.copyBytes(headerSrc, 56, mDexHeader.stringIdsSize);
        FileUtils.copyBytes(headerSrc, 60, mDexHeader.stringIdsOff);
        FileUtils.copyBytes(headerSrc, 64, mDexHeader.typeIdsSize);
        FileUtils.copyBytes(headerSrc, 68, mDexHeader.typeIdsOff);
        FileUtils.copyBytes(headerSrc, 72, mDexHeader.protoIdsSize);
        FileUtils.copyBytes(headerSrc, 76, mDexHeader.protoIdsOff);
        FileUtils.copyBytes(headerSrc, 80, mDexHeader.fieldIdsSize);
        FileUtils.copyBytes(headerSrc, 84, mDexHeader.fieldIdsOff);
        FileUtils.copyBytes(headerSrc, 88, mDexHeader.methodIdsSize);
        FileUtils.copyBytes(headerSrc, 92, mDexHeader.methodIdsOff);
        FileUtils.copyBytes(headerSrc, 96, mDexHeader.classDefsSize);
        FileUtils.copyBytes(headerSrc, 100, mDexHeader.classDefsOff);
        FileUtils.copyBytes(headerSrc, 104, mDexHeader.dataSize);
        FileUtils.copyBytes(headerSrc, 108, mDexHeader.dataOff);
    }
}
