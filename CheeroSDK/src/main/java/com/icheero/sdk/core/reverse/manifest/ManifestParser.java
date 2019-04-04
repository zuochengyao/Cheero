package com.icheero.sdk.core.reverse.manifest;

import android.text.TextUtils;

import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;
import com.icheero.sdk.util.XmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManifestParser implements IParser
{
    private static final Class TAG = ManifestParser.class;

    private byte[] mManifestData;
    private Manifest mManifest;
    private Map<String, String> mUriPrefixMap = new HashMap<>();
    private StringBuilder mBuilder;

    public ManifestParser(byte[] manifestData)
    {
        this.mManifestData = manifestData;
        this.mManifest = new Manifest();
        this.mBuilder = new StringBuilder();
    }

    @Override
    public void parse()
    {
        int sizeValue = 0;
        int nextChunkOffset = 0;

        Log.e(TAG, "Parse manifest start!");
        while (nextChunkOffset < mManifestData.length)
        {
            byte[] signature = FileUtils.copyBytes(mManifestData, nextChunkOffset, 4);
            int signatureValue = FileUtils.byte2Int(signature);
            byte[] size = FileUtils.copyBytes(mManifestData, nextChunkOffset + 4, 4);
            if (nextChunkOffset == 0)
            {
                // 判断：如果文件Header部分不是Manifest类型，则抛出异常
                if (Manifest.MANIFEST_HEADER != signatureValue)
                    throw new RuntimeException("The file is not a Manifest type!");
                mManifest.getHeader().hMagic = signature;
                mManifest.getHeader().hSize = size;
                Log.i(TAG, mManifest.getHeader().toString().split("\n"));
                sizeValue += 8;
                mBuilder.append(XmlUtils.createDeclaration("1.0", "utf-8"));
            }
            else
            {
                sizeValue = FileUtils.byte2Int(size);
                byte[] source = FileUtils.copyBytes(mManifestData, nextChunkOffset, sizeValue);
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
                        Manifest.StartNamespaceChunk chunk = parseStartNamespaceChunk(source);
                        mManifest.getStartNamespaceChunkList().add(chunk);
                        mUriPrefixMap.put(chunk.getUriStr(), chunk.getPrefixStr());
                        Log.i(TAG, chunk.toString().split("\n"));
                        break;
                    }
                    case Manifest.MANIFEST_END_NAMESPACE_CHUNK:
                    {
                        Manifest.EndNamespaceChunk chunk = parseEndNamespaceChunk(source);
                        Log.i(TAG, chunk.toString().split("\n"));
                        mManifest.getEndNamespaceChunkList().add(chunk);
                        break;
                    }
                    case Manifest.MANIFEST_START_TAG_CHUNK:
                    {
                        Manifest.StartTagChunk chunk = parseStartTagChunk(source);
                        mManifest.getStartTagChunkList().add(chunk);
                        createStartTag(chunk);
                        Log.i(TAG, chunk.toString().split("\n"));
                        break;
                    }
                    case Manifest.MANIFEST_END_TAG_CHUNK:
                    {
                        Manifest.EndTagChunk chunk = parseEndTagChunk(source);
                        mManifest.getEndTagChunkList().add(chunk);
                        mBuilder.append(XmlUtils.createEndTag(chunk.getNameStr()));
                        Log.i(TAG, chunk.toString().split("\n"));
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
        Log.e(TAG, "Parse manifest finish!");
        Log.i(TAG,("parse xml:\n" + mBuilder.toString()).split("\n"));
    }

    private void createStartTag(Manifest.StartTagChunk startTagChunk)
    {
        if ("manifest".equals(startTagChunk.getNameStr()))
        {
            mBuilder.append("<manifest ");
            for (Manifest.StartNamespaceChunk startNamespaceChunk : mManifest.getStartNamespaceChunkList())
            {
                mBuilder.append("xmls:").append(startNamespaceChunk.getPrefixStr());
                mBuilder.append("=");
                mBuilder.append("\"").append(startNamespaceChunk.getUriStr()).append("\"");
                mBuilder.append("\n");
            }
        }
        else
            mBuilder.append("<").append(startTagChunk.getNameStr());
        if (startTagChunk.stcAttributeList != null && startTagChunk.stcAttributeList.size() > 0)
        {
            if (!"manifest".equals(startTagChunk.getNameStr()))
                mBuilder.append("\n");
            for (int i = 0; i < startTagChunk.stcAttributeList.size(); i++)
            {
                Manifest.StartTagChunk.AttributeChunk attributeChunk = startTagChunk.stcAttributeList.get(i);
                if (!TextUtils.isEmpty(attributeChunk.getNamespaceUriStr()))
                    mBuilder.append(mUriPrefixMap.get(attributeChunk.getNamespaceUriStr())).append(":");
                mBuilder.append(attributeChunk.getNameStr()).append("=").append("\"");
                mBuilder.append(attributeChunk.getDataStr()).append("\"");
                mBuilder.append(i != startTagChunk.stcAttributeList.size() - 1 ? "\n" : "");
            }
        }
        mBuilder.append(">\n");
    }

    // region StringChunk

    private void parseStringChunk(byte[] stringChunk)
    {
        mManifest.getStringChunk().scSignature = FileUtils.copyBytes(stringChunk, 0, 4);
        mManifest.getStringChunk().scSize = FileUtils.copyBytes(stringChunk, 4, 4);
        mManifest.getStringChunk().scStringCount = FileUtils.copyBytes(stringChunk, 8, 4);
        mManifest.getStringChunk().scStyleCount = FileUtils.copyBytes(stringChunk, 12, 4);
        mManifest.getStringChunk().scUnknown = FileUtils.copyBytes(stringChunk, 16, 4);
        mManifest.getStringChunk().scStringPoolOffset = FileUtils.copyBytes(stringChunk, 20, 4);
        mManifest.getStringChunk().scStylePoolOffset = FileUtils.copyBytes(stringChunk, 24, 4);
        parseScStringPoolContent(stringChunk);
    }

    private void parseScStringPoolContent(byte[] chunk)
    {
        int scStringCountValue = mManifest.getStringChunk().getStringCountValue();
        int scStringPoolOffsetValue = mManifest.getStringChunk().getStringPoolOffsetValue();
        mManifest.getStringChunk().scStringPoolContent = FileUtils.copyBytes(chunk, scStringPoolOffsetValue, chunk.length - scStringPoolOffsetValue);
        mManifest.getStringChunk().scStringPoolContentList = new ArrayList<>(scStringCountValue);
        int endStringIndex = 0;
        for (int i = 0; i < scStringCountValue; i++)
        {
            // 这里的格式是：偏移值开始的两个字节是字符串的长度，接着是字符串的内容，后面跟着两个字符串的结束符00
            byte[] stringSize = FileUtils.copyBytes(mManifest.getStringChunk().scStringPoolContent, endStringIndex, 2);
            // 一个字符对应两个字节，所以要乘以2
            int stringSizeValue = FileUtils.byte2Short(stringSize) * 2;
            String str = new String(FileUtils.copyBytes(mManifest.getStringChunk().scStringPoolContent, endStringIndex + 2, stringSizeValue + 2));
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
        mManifest.getResourceIdChunk().rcSignature = FileUtils.copyBytes(resourceIdChunk, 0, 4);
        mManifest.getResourceIdChunk().rcSize = FileUtils.copyBytes(resourceIdChunk, 4, 4);
        parseResourceIdList(resourceIdChunk);
    }

    private void parseResourceIdList(byte[] chunk)
    {
        // chunkSize的值 包含了chunkTag和chunkSize，所以要去除这8个字节
        int resIdSize = mManifest.getResourceIdChunk().getSizeValue();
        mManifest.getResourceIdChunk().rcResourceId = FileUtils.copyBytes(chunk, 8, resIdSize - 8);
        mManifest.getResourceIdChunk().rcResourceIdList = new ArrayList<>(mManifest.getResourceIdChunk().rcResourceId.length / 4);
        for (int i = 0; i < mManifest.getResourceIdChunk().rcResourceId.length / 4; i++)
        {
            byte[] resId = FileUtils.copyBytes(mManifest.getResourceIdChunk().rcResourceId, i * 4, 4);
            int resIdValue = FileUtils.byte2Int(resId);
            mManifest.getResourceIdChunk().rcResourceIdList.add(resIdValue);
        }
    }

    // endregion

    // region Namespace Chunk

    private Manifest.StartNamespaceChunk parseStartNamespaceChunk(byte[] startNamespaceChunk)
    {
        Manifest.StartNamespaceChunk chunk = mManifest.new StartNamespaceChunk();
        chunk.sncSignature = FileUtils.copyBytes(startNamespaceChunk, 0, 4);
        chunk.sncSize = FileUtils.copyBytes(startNamespaceChunk, 4, 4);
        chunk.sncLineNumber = FileUtils.copyBytes(startNamespaceChunk, 8, 4);
        chunk.sncUnknown = FileUtils.copyBytes(startNamespaceChunk, 12, 4);
        chunk.sncPrefix = FileUtils.copyBytes(startNamespaceChunk, 16, 4);
        chunk.sncUri = FileUtils.copyBytes(startNamespaceChunk, 20, 4);
        return chunk;
    }

    private Manifest.EndNamespaceChunk parseEndNamespaceChunk(byte[] endNamespaceChunk)
    {
        Manifest.EndNamespaceChunk chunk = mManifest.new EndNamespaceChunk();
        chunk.encSignature = FileUtils.copyBytes(endNamespaceChunk, 0, 4);
        chunk.encSize = FileUtils.copyBytes(endNamespaceChunk, 4, 4);
        chunk.encLineNumber = FileUtils.copyBytes(endNamespaceChunk, 8, 4);
        chunk.encUnknown = FileUtils.copyBytes(endNamespaceChunk, 12, 4);
        chunk.encPrefix = FileUtils.copyBytes(endNamespaceChunk, 16, 4);
        chunk.encUri = FileUtils.copyBytes(endNamespaceChunk, 20, 4);
        return chunk;
    }

    // endregion

    // region Tag Chunk

    private Manifest.StartTagChunk parseStartTagChunk(byte[] startTagChunk)
    {
        Manifest.StartTagChunk chunk = mManifest.new StartTagChunk();
        chunk.stcSignature = FileUtils.copyBytes(startTagChunk, 0, 4);
        chunk.stcSize = FileUtils.copyBytes(startTagChunk, 4, 4);
        chunk.stcLineNumber = FileUtils.copyBytes(startTagChunk, 8, 4);
        chunk.stcUnknown = FileUtils.copyBytes(startTagChunk, 12, 4);
        chunk.stcNamespaceUri = FileUtils.copyBytes(startTagChunk, 16, 4);
        chunk.stcName = FileUtils.copyBytes(startTagChunk, 20, 4);
        chunk.stcFlags = FileUtils.copyBytes(startTagChunk, 24, 4);
        chunk.stcAttributeCount = FileUtils.copyBytes(startTagChunk, 28, 4);
        chunk.stcClassAttribute = FileUtils.copyBytes(startTagChunk, 32, 4);
        chunk.stcAttributeChunk = FileUtils.copyBytes(startTagChunk, 36, startTagChunk.length - 36);
        parseAttributeChunk(chunk);
        return chunk;
    }

    private void parseAttributeChunk(Manifest.StartTagChunk chunk)
    {
        chunk.stcAttributeList = new ArrayList<>(chunk.getAttributeCountValue());
        for (int i = 0; i < chunk.getAttributeCountValue(); i++)
        {
            Manifest.StartTagChunk.AttributeChunk attribute = chunk.new AttributeChunk();
            attribute.acNamespaceUri = FileUtils.copyBytes(chunk.stcAttributeChunk,  20 * i, 4);
            attribute.acName = FileUtils.copyBytes(chunk.stcAttributeChunk, 20 * i + 4, 4);
            attribute.acValueStr = FileUtils.copyBytes(chunk.stcAttributeChunk, 20 * i + 8, 4);
            attribute.acType = FileUtils.copyBytes(chunk.stcAttributeChunk,   20 * i + 12, 4);
            attribute.acData = FileUtils.copyBytes(chunk.stcAttributeChunk,   20 * i + 16, 4);
            chunk.stcAttributeList.add(attribute);
        }
    }

    private Manifest.EndTagChunk parseEndTagChunk(byte[] endTagChunk)
    {
        Manifest.EndTagChunk chunk = mManifest.new EndTagChunk();
        chunk.etcSignature = FileUtils.copyBytes(endTagChunk, 0, 4);
        chunk.etcSize = FileUtils.copyBytes(endTagChunk, 4, 4);
        chunk.etcLineNumber = FileUtils.copyBytes(endTagChunk, 8, 4);
        chunk.etcUnknown = FileUtils.copyBytes(endTagChunk, 12, 4);
        chunk.etcNamespaceUri = FileUtils.copyBytes(endTagChunk, 16, 4);
        chunk.etcName = FileUtils.copyBytes(endTagChunk, 20, 4);
        return chunk;
    }

    // endregion

}
