package com.icheero.reverse.manifest;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;

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

    public void parseManifest()
    {
        int sizeValue = 0;
        int nextChunkOffset = 0;
        while (nextChunkOffset < mManifestData.length)
        {
            byte[] signature = Common.copyBytes(mManifestData, nextChunkOffset, 4);
            int signatureValue = Common.byte2Int(signature);
            byte[] size = Common.copyBytes(mManifestData, nextChunkOffset + 4, 4);
            if (nextChunkOffset == 0)
            {
                // 判断：如果文件Header部分不是Manifest类型，则抛出异常
                if (Manifest.MANIFEST_HEADER != signatureValue)
                    throw new RuntimeException("The file is not a Manifest type!");
                mManifest.getHeader().hMagic = signature;
                mManifest.getHeader().hSize = size;
                Log.i(TAG, mManifest.getHeader().toString().split("\n"));
                sizeValue += 8;
            }
            else
            {
                sizeValue = Common.byte2Int(size);
                byte[] source = Common.copyBytes(mManifestData, nextChunkOffset, sizeValue);
                switch (signatureValue)
                {
                    case Manifest.MANIFEST_STRING_CHUNK:
                    {
                        parseStringChunk(source);
                        Log.i(TAG, mManifest.getStringChunk().toString().split("\n"));
                        break;
                    }
                    case Manifest.MANIFEST_RESOURCE_CHUNK:
                    {
                        parseResourceIdChunk(source);
                        Log.i(TAG, mManifest.getResourceIdChunk().toString().split("\n"));
                        break;
                    }
                    case Manifest.MANIFEST_START_NAMESPACE_CHUNK:
                    {
                        parseStartNamespaceChunk(source);
                        Log.i(TAG, mManifest.getStartNamespaceChunk().toString().split("\n"));
                        break;
                    }
                    case Manifest.MANIFEST_END_NAMESPACE_CHUNK:
                    {
                        parseEndNamespaceChunk(source);
                        break;
                    }
                    case Manifest.MANIFEST_START_TAG_CHUNK:
                    {
                        parseStartTagChunk(source);
                        Log.i(TAG, mManifest.getStartTagChunk().toString().split("\n"));
                        break;
                    }
                    case Manifest.MANIFEST_END_TAG_CHUNK:
                    {
                        parseEndTagChunk(source);
                        break;
                    }
                    default:
                    {
                        Log.e(TAG, "Parse failed!");
                    }
                }
            }
            nextChunkOffset += sizeValue;
        }
    }

    // region StringChunk

    private void parseStringChunk(byte[] stringChunk)
    {
        mManifest.getStringChunk().scSignature = Common.copyBytes(stringChunk, 0, 4);
        mManifest.getStringChunk().scSize = Common.copyBytes(stringChunk, 4, 4);
        mManifest.getStringChunk().scStringCount = Common.copyBytes(stringChunk, 8, 4);
        mManifest.getStringChunk().scStyleCount = Common.copyBytes(stringChunk, 12, 4);
        mManifest.getStringChunk().scUnknown = Common.copyBytes(stringChunk, 16, 4);
        mManifest.getStringChunk().scStringPoolOffset = Common.copyBytes(stringChunk, 20, 4);
        mManifest.getStringChunk().scStylePoolOffset = Common.copyBytes(stringChunk, 24, 4);
        parseScStringPoolContent(stringChunk);
    }

    private void parseScStringPoolContent(byte[] chunk)
    {
        int scStringCountValue = mManifest.getStringChunk().getStringCountValue();
        int scStringPoolOffsetValue = mManifest.getStringChunk().getStringPoolOffsetValue();
        mManifest.getStringChunk().scStringPoolContent = Common.copyBytes(chunk, scStringPoolOffsetValue, chunk.length - scStringPoolOffsetValue);
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
        if (str == null || str.length() == 0) return str;
        byte[] strBytes = str.getBytes();
        ArrayList<Byte> newByte = new ArrayList<>();

        for (byte strByte : strBytes)
        {
            if (strByte != 0) newByte.add(strByte);
        }
        byte[] newByteAry = new byte[newByte.size()];
        for (int i = 0; i < newByteAry.length; i++)
            newByteAry[i] = newByte.get(i);
        return new String(newByteAry);
    }
    // endregion

    // region ResourceIdChunk

    private void parseResourceIdChunk(byte[] resourceIdChunk)
    {
        mManifest.getResourceIdChunk().rcSignature = Common.copyBytes(resourceIdChunk, 0, 4);
        mManifest.getResourceIdChunk().rcSize = Common.copyBytes(resourceIdChunk, 4, 4);
        parseResourceIdList(resourceIdChunk);
    }

    private void parseResourceIdList(byte[] chunk)
    {
        // chunkSize的值 包含了chunkTag和chunkSize，所以要去除这8个字节
        int resIdSize = mManifest.getResourceIdChunk().getSizeValue();
        mManifest.getResourceIdChunk().rcResourceId = Common.copyBytes(chunk, 8, resIdSize - 8);
        mManifest.getResourceIdChunk().rcResourceIdList = new ArrayList<>(mManifest.getResourceIdChunk().rcResourceId.length / 4);
        for (int i = 0; i < mManifest.getResourceIdChunk().rcResourceId.length / 4; i++)
        {
            byte[] resId = Common.copyBytes(mManifest.getResourceIdChunk().rcResourceId, i * 4, 4);
            int resIdValue = Common.byte2Int(resId);
            mManifest.getResourceIdChunk().rcResourceIdList.add(resIdValue);
        }
    }

    // endregion

    // region Namespace Chunk

    private void parseStartNamespaceChunk(byte[] startNamespaceChunk)
    {
        mManifest.getStartNamespaceChunk().sncSignature = Common.copyBytes(startNamespaceChunk, 0, 4);
        mManifest.getStartNamespaceChunk().sncSize = Common.copyBytes(startNamespaceChunk, 4, 4);
        mManifest.getStartNamespaceChunk().sncLineNumber = Common.copyBytes(startNamespaceChunk, 8, 4);
        mManifest.getStartNamespaceChunk().sncUnknown = Common.copyBytes(startNamespaceChunk, 12, 4);
        mManifest.getStartNamespaceChunk().sncPrefix = Common.copyBytes(startNamespaceChunk, 16, 4);
        mManifest.getStartNamespaceChunk().sncUri = Common.copyBytes(startNamespaceChunk, 20, 4);
    }

    private void parseEndNamespaceChunk(byte[] endNamespaceChunk)
    {
    }

    // endregion

    // region Tag Chunk

    private void parseStartTagChunk(byte[] startTagChunk)
    {
        mManifest.getStartTagChunk().stcSignature = Common.copyBytes(startTagChunk, 0, 4);
        mManifest.getStartTagChunk().stcSize = Common.copyBytes(startTagChunk, 4, 4);
        mManifest.getStartTagChunk().stcLineNumber = Common.copyBytes(startTagChunk, 8, 4);
        mManifest.getStartTagChunk().stcUnknown = Common.copyBytes(startTagChunk, 12, 4);
        mManifest.getStartTagChunk().stcNamespaceUri = Common.copyBytes(startTagChunk, 16, 4);
        mManifest.getStartTagChunk().stcName = Common.copyBytes(startTagChunk, 20, 4);
        mManifest.getStartTagChunk().stcFlags = Common.copyBytes(startTagChunk, 24, 4);
        mManifest.getStartTagChunk().stcAttributeCount = Common.copyBytes(startTagChunk, 28, 4);
        mManifest.getStartTagChunk().stcClassAttribute = Common.copyBytes(startTagChunk, 32, 4);
        parseAttributeChunk(Common.copyBytes(startTagChunk, 36, startTagChunk.length - 36));
    }

    private void parseAttributeChunk(byte[] attributeChunk)
    {
        mManifest.getStartTagChunk().stcAttributeChunk = attributeChunk;
        mManifest.getStartTagChunk().stcAttributeList = new ArrayList<>(mManifest.getStartTagChunk().getAttributeCountValue());
        for (int i = 0; i < mManifest.getStartTagChunk().getAttributeCountValue(); i++)
        {
            Manifest.StartTagChunk.AttributeChunk attribute = mManifest.getStartTagChunk().new AttributeChunk();
            attribute.acNamespaceUri = Common.copyBytes(attributeChunk,  20 * i, 4);
            attribute.acName = Common.copyBytes(attributeChunk, 20 * i + 4, 4);
            attribute.acValueStr = Common.copyBytes(attributeChunk, 20 * i + 8, 4);
            attribute.acType = Common.copyBytes(attributeChunk,   20 * i + 12, 4);
            attribute.acData = Common.copyBytes(attributeChunk,   20 * i + 16, 4);
            mManifest.getStartTagChunk().stcAttributeList.add(attribute);
        }
    }

    private void parseEndTagChunk(byte[] endTagChunk)
    {

    }


    // endregion
}
