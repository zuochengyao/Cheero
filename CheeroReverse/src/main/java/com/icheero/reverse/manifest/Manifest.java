package com.icheero.reverse.manifest;

import com.icheero.sdk.util.Common;

import java.util.List;

import androidx.annotation.NonNull;

class Manifest
{
    private Header mHeader;
    private StringChunk mStringChunk;
    private ResourceIdChunk mResourceIdChunk;

    Manifest()
    {
        mHeader = new Header();
        mStringChunk = new StringChunk();
        mResourceIdChunk = new ResourceIdChunk();
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

    public static class Header
    {
        private Header() {}

        byte[] h_magic;
        byte[] h_size;

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ Header ------------------\n");
            builder.append("MagicNumber: ").append(Common.byte2HexString(h_magic)).append("\n");
            builder.append("Size: ").append(Common.byte2HexString(h_size));
            return builder.toString();
        }
    }

    public static class StringChunk
    {
        private StringChunk() {}

        byte[] sc_signature;
        byte[] sc_size;
        byte[] sc_stringCount;
        byte[] sc_styleCount;
        byte[] sc_unknown;
        byte[] sc_stringPoolOffset;
        byte[] sc_stylePoolOffset;

        byte[] sc_stringPoolContent;
        List<String> sc_stringPoolContentList;

        int getSignatureValue()
        {
            return Common.byte2Int(sc_signature);
        }

        int getSizeValue()
        {
            return Common.byte2Int(sc_size);
        }

        int getStringCountValue()
        {
            return Common.byte2Int(sc_stringCount);
        }

        int getStyleCountValue()
        {
            return Common.byte2Int(sc_styleCount);
        }

        int getUnknownValue()
        {
            return Common.byte2Int(sc_unknown);
        }

        int getStringPoolOffsetValue()
        {
            return Common.byte2Int(sc_stringPoolOffset);
        }

        int getStylePoolOffsetValue()
        {
            return Common.byte2Int(sc_stylePoolOffset);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ StringChunk ------------------\n");
            builder.append("Signature: ").append(Common.byte2HexString(sc_signature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(Common.byte2HexString(sc_size)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("String Count: ").append(Common.byte2HexString(sc_stringCount)).append("(").append(getStringCountValue()).append(")").append("\n");
            builder.append("Style Count: ").append(Common.byte2HexString(sc_styleCount)).append("(").append(getStyleCountValue()).append(")").append("\n");
            builder.append("Unknown: ").append(Common.byte2HexString(sc_unknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("StringPool Offset: ").append(Common.byte2HexString(sc_stringPoolOffset)).append("(").append(getStringPoolOffsetValue()).append(")").append("\n");
            builder.append("StylePool Offset: ").append(Common.byte2HexString(sc_stylePoolOffset)).append("(").append(getStylePoolOffsetValue()).append(")").append("\n");
            for (String str : sc_stringPoolContentList)
                builder.append("str: ").append(str).append("\n");
            return builder.toString();
        }
    }

    public static class ResourceIdChunk
    {
        private ResourceIdChunk() {}

        byte[] rc_signature;
        byte[] rc_size;

        byte[] rc_resourceId;
        List<Integer> rc_resourceIdList;

        int getSignatureValue()
        {
            return Common.byte2Int(rc_signature);
        }

        int getSizeValue()
        {
            return Common.byte2Int(rc_size);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ ResourceIdChunk ------------------\n");
            builder.append("Signature: ").append(Common.byte2HexString(rc_signature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(Common.byte2HexString(rc_size)).append("(").append(getSizeValue()).append(")").append("\n");
            for (int redId : rc_resourceIdList)
                builder.append("resId: ").append(Common.byte2HexString(Common.int2Byte(redId))).append("(").append(redId).append(")").append("\n");
            return builder.toString();
        }
    }

}
