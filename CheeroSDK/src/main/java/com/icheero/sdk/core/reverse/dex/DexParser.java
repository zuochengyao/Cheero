package com.icheero.sdk.core.reverse.dex;

import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.core.reverse.dex.model.Dex;
import com.icheero.sdk.core.reverse.dex.model.DexHeader;
import com.icheero.sdk.core.reverse.dex.model.ProtoIdItem;
import com.icheero.sdk.core.reverse.dex.model.StringIdItem;
import com.icheero.sdk.core.reverse.dex.model.TypeIdItem;
import com.icheero.sdk.core.reverse.dex.model.TypeItem;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DexParser implements IParser
{
    private static final Class TAG = DexParser.class;

    private byte[] mDexData;
    private Dex mDex;
    private DexHeader mDexHeader;
    private static List<String> mStringList;
    private static List<String> mTypeList;

    public DexParser(byte[] dexData)
    {
        this.mDexData = dexData;
        mDexHeader = new DexHeader();
        mDex = new Dex(mDexHeader);
        mStringList = new ArrayList<>();
        mTypeList = new ArrayList<>();
    }

    @Override
    public void parse()
    {
        Log.e(TAG, "Parse dex start!");
        parseDexHeader();
        parseDexStringIdList();
        parseTypeIdList();
        parseProtoIdList();
        Log.e(TAG, "Parse dex finish!");
    }

    private void parseDexHeader()
    {
        FileUtils.copyBytes(mDexData, 0, mDexHeader.magic);
        FileUtils.copyBytes(mDexData, 8, mDexHeader.checkSum);
        FileUtils.copyBytes(mDexData, 12, mDexHeader.signature);
        FileUtils.copyBytes(mDexData, 32, mDexHeader.fileSize);
        FileUtils.copyBytes(mDexData, 36, mDexHeader.headerSize);
        FileUtils.copyBytes(mDexData, 40, mDexHeader.endianTag);
        FileUtils.copyBytes(mDexData, 44, mDexHeader.linkSize);
        FileUtils.copyBytes(mDexData, 48, mDexHeader.linkOff);
        FileUtils.copyBytes(mDexData, 52, mDexHeader.mapOff);
        FileUtils.copyBytes(mDexData, 56, mDexHeader.stringIdsSize);
        FileUtils.copyBytes(mDexData, 60, mDexHeader.stringIdsOff);
        FileUtils.copyBytes(mDexData, 64, mDexHeader.typeIdsSize);
        FileUtils.copyBytes(mDexData, 68, mDexHeader.typeIdsOff);
        FileUtils.copyBytes(mDexData, 72, mDexHeader.protoIdsSize);
        FileUtils.copyBytes(mDexData, 76, mDexHeader.protoIdsOff);
        FileUtils.copyBytes(mDexData, 80, mDexHeader.fieldIdsSize);
        FileUtils.copyBytes(mDexData, 84, mDexHeader.fieldIdsOff);
        FileUtils.copyBytes(mDexData, 88, mDexHeader.methodIdsSize);
        FileUtils.copyBytes(mDexData, 92, mDexHeader.methodIdsOff);
        FileUtils.copyBytes(mDexData, 96, mDexHeader.classDefsSize);
        FileUtils.copyBytes(mDexData, 100, mDexHeader.classDefsOff);
        FileUtils.copyBytes(mDexData, 104, mDexHeader.dataSize);
        FileUtils.copyBytes(mDexData, 108, mDexHeader.dataOff);
        Log.i(TAG, mDexHeader.toString().split("\n"));
    }

    private void parseDexStringIdList()
    {
        if (mDexHeader.getStringIdsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ DexStringIdList ------------------\n");
            for (int i = 0; i < mDexHeader.getStringIdsSizeValue(); i++)
            {
                StringIdItem stringId = new StringIdItem();
                FileUtils.copyBytes(mDexData, mDexHeader.getStringIdsOffValue() + i * 4, stringId.stringDataOff);
                stringId.stringData.val = mDexData[stringId.getStringDataOffValue()];
                stringId.stringData.data = FileUtils.copyBytes(mDexData, stringId.getStringDataOffValue() + 1, stringId.stringData.val);
                Log.i(TAG, stringId.toString());
                mDex.stringIds.add(stringId);
                mStringList.add(stringId.stringData.getDataStr());
            }
        }
    }

    private void parseTypeIdList()
    {
        if (mDexHeader.getTypeIdsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ DexTypeIdList ------------------\n");
            for (int i = 0; i < mDexHeader.getTypeIdsSizeValue(); i++)
            {
                TypeIdItem typeId = new TypeIdItem();
                FileUtils.copyBytes(mDexData, mDexHeader.getTypeIdsOffValue() + i * 4, typeId.descriptorIdx);
                String type = mDex.stringIds.get(typeId.getDescriptorIdx()).stringData.getDataStr();
                Log.i(TAG, typeId.toString().concat(", DataStr: " + type));
                mDex.typeIds.add(typeId);
                mTypeList.add(type);
            }
        }
    }

    private void parseProtoIdList()
    {
        if (mDexHeader.getProtoIdsSizeValue() > 0)
        {
            Log.i(TAG, "------------------ DexProtoIdList ------------------\n");
            for (int i = 0; i < mDexHeader.getProtoIdsSizeValue(); i++)
            {
                ProtoIdItem protoId = new ProtoIdItem();
                FileUtils.copyBytes(mDexData, mDexHeader.getProtoIdsOffValue() + i * 12, protoId.shortyIdx);
                FileUtils.copyBytes(mDexData, mDexHeader.getProtoIdsOffValue() + i * 12 + 4, protoId.returnTypeIdx);
                FileUtils.copyBytes(mDexData, mDexHeader.getProtoIdsOffValue() + i * 12 + 8, protoId.parametersOff);
                if (protoId.getParametersOffValue() > 0)
                {
                    int size = FileUtils.byte2Int(FileUtils.copyBytes(mDexData, protoId.getParametersOffValue(), 4));
                    for (int j = 0; j < size; j++)
                    {
                        TypeItem type = new TypeItem();
                        FileUtils.copyBytes(mDexData, protoId.getParametersOffValue() + 4 + j * 2, type.typeIdx);
                        protoId.parameters.size = size;
                        protoId.parameters.list.add(type);
                    }
                }
                Log.i(TAG, protoId.toString());
                mDex.protoIds.add(protoId);
            }
        }
    }

    public static String getDataString(int index)
    {
        return mStringList.get(index);
    }

    public static String getTypeString(int index)
    {
        return mTypeList.get(index);
    }
}
