package com.icheero.reverse.manifest;

import com.icheero.sdk.util.Common;

import java.util.List;

import androidx.annotation.NonNull;

@SuppressWarnings("StringBufferReplaceableByString")
class Manifest
{
    private Header mHeader;
    private StringChunk mStringChunk;
    private ResourceIdChunk mResourceIdChunk;
    private StartNamespaceChunk mStartNamespaceChunk;

    Manifest()
    {
        mHeader = new Header();
        mStringChunk = new StringChunk();
        mResourceIdChunk = new ResourceIdChunk();
        mStartNamespaceChunk = new StartNamespaceChunk();
    }

    Header getHeader()
    {
        return mHeader;
    }

    StringChunk getStringChunk()
    {
        return mStringChunk;
    }

    ResourceIdChunk getResourceIdChunk()
    {
        return mResourceIdChunk;
    }

    StartNamespaceChunk getStartNamespaceChunk()
    {
        return mStartNamespaceChunk;
    }

    public class Header
    {
        private Header() {}
        /** 文件魔数 */
        byte[] hMagic;
        /** 文件大小 */
        byte[] hSize;

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ Header ------------------\n");
            builder.append("MagicNumber: ").append(Common.byte2HexString(hMagic)).append("\n");
            builder.append("Size: ").append(Common.byte2HexString(hSize));
            return builder.toString();
        }
    }

    public class StringChunk
    {
        private StringChunk() {}

        /** StringChunk的类型，固定四个字节：0x001C0001 */
        byte[] scSignature;
        /** StringChunk的大小：0x001C0001 */
        byte[] scSize;
        /** StringChunk中字符串的个数 */
        byte[] scStringCount;
        /** StringChunk中样式的个数，四个字节，但是在实际解析过程中，这个值一直是0x00000000 */
        byte[] scStyleCount;
        /** 未知区域 */
        byte[] scUnknown;
        /** 字符串池的偏移值，这个偏移值是相对于StringChunk的头部位置 */
        byte[] scStringPoolOffset;
        /** 样式池的偏移值，可忽略 */
        byte[] scStylePoolOffset;
        /** 字符串池内容块 */
        byte[] scStringPoolContent;
        List<String> scStringPoolContentList;

        int getSignatureValue()
        {
            return Common.byte2Int(scSignature);
        }

        int getSizeValue()
        {
            return Common.byte2Int(scSize);
        }

        int getStringCountValue()
        {
            return Common.byte2Int(scStringCount);
        }

        int getStyleCountValue()
        {
            return Common.byte2Int(scStyleCount);
        }

        int getUnknownValue()
        {
            return Common.byte2Int(scUnknown);
        }

        int getStringPoolOffsetValue()
        {
            return Common.byte2Int(scStringPoolOffset);
        }

        int getStylePoolOffsetValue()
        {
            return Common.byte2Int(scStylePoolOffset);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ StringChunk ------------------\n");
            builder.append("Signature: ").append(Common.byte2HexString(scSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(Common.byte2HexString(scSize)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("String Count: ").append(Common.byte2HexString(scStringCount)).append("(").append(getStringCountValue()).append(")").append("\n");
            builder.append("Style Count: ").append(Common.byte2HexString(scStyleCount)).append("(").append(getStyleCountValue()).append(")").append("\n");
            builder.append("Unknown: ").append(Common.byte2HexString(scUnknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("StringPool Offset: ").append(Common.byte2HexString(scStringPoolOffset)).append("(").append(getStringPoolOffsetValue()).append(")").append("\n");
            builder.append("StylePool Offset: ").append(Common.byte2HexString(scStylePoolOffset)).append("(").append(getStylePoolOffsetValue()).append(")").append("\n");
            for (String str : scStringPoolContentList)
                builder.append("str: ").append(str).append("\n");
            return builder.toString();
        }
    }

    public class ResourceIdChunk
    {
        private ResourceIdChunk() {}

        /** ResourceIdChunk的类型，固定四个字节：0x00080108 */
        byte[] rcSignature;
        /** ResourceChunk的大小 */
        byte[] rcSize;
        /** ResourceId的内容，这里大小是ResourceChunk大小除以4，减去头部的大小8个字节 */
        byte[] rcResourceId;
        List<Integer> rcResourceIdList;

        int getSignatureValue()
        {
            return Common.byte2Int(rcSignature);
        }

        int getSizeValue()
        {
            return Common.byte2Int(rcSize);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ ResourceIdChunk ------------------\n");
            builder.append("Signature: ").append(Common.byte2HexString(rcSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(Common.byte2HexString(rcSize)).append("(").append(getSizeValue()).append(")").append("\n");
            for (int redId : rcResourceIdList)
                builder.append("resId: ").append(Common.byte2HexString(Common.int2Byte(redId))).append("(").append(redId).append(")").append("\n");
            return builder.toString();
        }
    }

    public class StartNamespaceChunk
    {
        private StartNamespaceChunk() {}
        /** Chunk的类型，固定四个字节：0x00100100 */
        byte[] sncSignature;
        /** Chunk的大小 */
        byte[] sncSize;
        /** 在AndroidManifest文件中的行号 */
        byte[] sncLineNumber;
        /** 未知区域 */
        byte[] sncUnknown;
        /** 命名空间的前缀(在字符串中的索引值)，比如：android */
        byte[] sncPrefix;
        /** 命名空间的uri(在字符串中的索引值)：比如：http://schemas.android.com/apk/res/android */
        byte[] sncUri;

        int getSignatureValue()
        {
            return Common.byte2Int(sncSignature);
        }

        int getSizeValue()
        {
            return Common.byte2Int(sncSize);
        }

        int getLineNumberValue()
        {
            return Common.byte2Int(sncLineNumber);
        }

        int getUnknownValue()
        {
            return Common.byte2Int(sncUnknown);
        }

        int getPrefixValue()
        {
            return Common.byte2Int(sncPrefix);
        }

        int getUriValue()
        {
            return Common.byte2Int(sncUri);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ StartNamespaceChunk ------------------\n");
            builder.append("Signature: ").append(Common.byte2HexString(sncSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(Common.byte2HexString(sncSize)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("LineNumber: ").append(Common.byte2HexString(sncLineNumber)).append("(").append(getLineNumberValue()).append(")").append("\n");
            builder.append("Unknown: ").append(Common.byte2HexString(sncUnknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("Prefix: ").append(Common.byte2HexString(sncPrefix)).append("(").append(getPrefixValue()).append(")").append("\n");
            builder.append("Prefix: ").append(mStringChunk.scStringPoolContentList.get(getPrefixValue())).append("\n");
            builder.append("Uri: ").append(Common.byte2HexString(sncUri)).append("(").append(getUriValue()).append(")").append("\n");
            builder.append("Uri: ").append(mStringChunk.scStringPoolContentList.get(getUriValue())).append("\n");
            return builder.toString();
        }
    }

}
