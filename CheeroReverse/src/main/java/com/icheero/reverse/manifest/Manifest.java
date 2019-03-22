package com.icheero.reverse.manifest;

import com.icheero.sdk.util.Common;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

@SuppressWarnings("StringBufferReplaceableByString")
class Manifest
{
    public static final int MANIFEST_HEADER = 0x00080003;
    public static final int MANIFEST_STRING_CHUNK = 0x001c0001;
    public static final int MANIFEST_RESOURCE_CHUNK = 0x00080180;
    public static final int MANIFEST_START_NAMESPACE_CHUNK = 0x00100100;
    public static final int MANIFEST_END_NAMESPACE_CHUNK = 0x00100101;
    public static final int MANIFEST_START_TAG_CHUNK = 0x00100102;
    public static final int MANIFEST_END_TAG_CHUNK = 0x00100103;
    public static final int MANIFEST_TEXT_CHUNK = 0x00100104;

    private Header mHeader;
    private StringChunk mStringChunk;
    private ResourceIdChunk mResourceIdChunk;
    private StartNamespaceChunk mStartNamespaceChunk;
    private StartTagChunk mStartTagChunk;

    Manifest()
    {
        mHeader = new Header();
        mStringChunk = new StringChunk();
        mResourceIdChunk = new ResourceIdChunk();
        mStartNamespaceChunk = new StartNamespaceChunk();
        mStartTagChunk = new StartTagChunk();
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

    StartTagChunk getStartTagChunk()
    {
        return mStartTagChunk;
    }

    class Header
    {
        private Header() {}
        /** 文件魔数 */
        byte[] hMagic;
        /** 文件大小 */
        byte[] hSize;

        int getMagicNumberValue()
        {
            return Common.byte2Int(hMagic);
        }

        /**
         * @return Manifest 总长度
         */
        int getSizeValue()
        {
            return Common.byte2Int(hSize);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ Header ------------------\n");
            builder.append("MagicNumber: ").append(Common.byte2HexString(hMagic)).append("(").append(getMagicNumberValue()).append(")").append("\n");
            builder.append("Size: ").append(Common.byte2HexString(hSize)).append("(").append(getSizeValue()).append(")");
            return builder.toString();
        }
    }

    class StringChunk
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
            for (int i = 0; i < scStringPoolContentList.size(); i++)
            {
                builder.append("str[").append(i).append("]: ").append(scStringPoolContentList.get(i));
                if (i != scStringPoolContentList.size() - 1)
                    builder.append("\n");
            }
            return builder.toString();
        }
    }

    class ResourceIdChunk
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
            for (int i = 0; i < rcResourceIdList.size(); i++)
            {
                builder.append("resId[").append(i).append("]: ");
                builder.append(Common.byte2HexString(Common.int2Byte(rcResourceIdList.get(i))));
                builder.append("(").append(rcResourceIdList.get(i)).append(")");
                if (i != rcResourceIdList.size() - 1)
                    builder.append("\n");
            }
            return builder.toString();
        }
    }

    class StartNamespaceChunk
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

    class StartTagChunk
    {
        private StartTagChunk() {}

        /** StartTagChunk的类型，固定四个字节：0x00100102 */
        byte[] stcSignature;
        /** StartTagChunk的大小 */
        byte[] stcSize;
        /** 对应于AndroidManifest中的行号 */
        byte[] stcLineNumber;
        /** 未知区域 */
        byte[] stcUnknown;
        /**
         * 这个标签用到的命名空间的Uri
         * 比如用到了android这个前缀，那么就需要用http://schemas.android.com/apk/res/android这个Uri去获取
         */
        byte[] stcNamespaceUri;
        /** 标签名称(在字符串中的索引值) */
        byte[] stcName;
        /** 标签的类型，四个字节，比如是开始标签还是结束标签等 */
        byte[] stcFlags;
        /** 标签包含的属性个数 */
        byte[] stcAttributeCount;
        /** 标签包含的类属性 */
        byte[] stcClassAttribute;
        /** 
         * 属性内容
         * 每个属性算是一个Entry，这个Entry固定大小是大小为5的字节数组：
         * 我们在解析的时候需要注意第四个值，要做一次处理：需要右移24位。所以这个字段的大小是：属性个数*5*4个字节
         */
        byte[] stcAttributeChunk;
        ArrayList<AttributeChunk> stcAttributeList;

        int getSignatureValue()
        {
            return Common.byte2Int(stcSignature);
        }

        int getSizeValue()
        {
            return Common.byte2Int(stcSize);
        }

        int getLineNumberValue()
        {
            return Common.byte2Int(stcLineNumber);
        }

        int getUnknownValue()
        {
            return Common.byte2Int(stcUnknown);
        }

        int getNamespaceUriValue()
        {
            return Common.byte2Int(stcNamespaceUri);
        }

        int getNameValue()
        {
            return Common.byte2Int(stcName);
        }

        int getFlagsValue()
        {
            return Common.byte2Int(stcFlags);
        }

        int getAttributeCountValue()
        {
            return Common.byte2Int(stcAttributeCount);
        }

        int getClassAttributeValue()
        {
            return Common.byte2Int(stcClassAttribute);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ StartTagChunk ------------------\n");
            builder.append("Signature: ").append(Common.byte2HexString(stcSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(Common.byte2HexString(stcSize)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("LineNumber: ").append(Common.byte2HexString(stcLineNumber)).append("(").append(getLineNumberValue()).append(")").append("\n");
            builder.append("Unknown: ").append(Common.byte2HexString(stcUnknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("NamespaceUri: ").append(Common.byte2HexString(stcNamespaceUri)).append("(").append(getNamespaceUriValue()).append(")").append("\n");
            if (getNamespaceUriValue() != -1 && getNamespaceUriValue() < mStringChunk.scStringPoolContentList.size())
                builder.append("Uri Str: ").append(mStringChunk.scStringPoolContentList.get(getNamespaceUriValue())).append("\n");
            builder.append("TagName: ").append(Common.byte2HexString(stcName)).append("(").append(getNameValue()).append(")").append("\n");
            if (getNameValue() != -1 && getNameValue() < mStringChunk.scStringPoolContentList.size())
                builder.append("TagName Str: ").append(mStringChunk.scStringPoolContentList.get(getNameValue())).append("\n");
            builder.append("Flags: ").append(Common.byte2HexString(stcFlags)).append("(").append(getFlagsValue()).append(")").append("\n");
            builder.append("AttributeCount: ").append(Common.byte2HexString(stcAttributeCount)).append("(").append(getAttributeCountValue()).append(")").append("\n");
            builder.append("ClassAttribute: ").append(Common.byte2HexString(stcClassAttribute)).append("(").append(getClassAttributeValue()).append(")").append("\n");
            for (int i = 0; i < stcAttributeList.size(); i++)
            {
                builder.append("Attribute[").append(i).append("]: ");
                builder.append(stcAttributeList.get(i).toString());
                if (i != stcAttributeList.size() - 1)
                    builder.append("\n");
            }
            return builder.toString();
        }

        class AttributeChunk
        {
            byte[] acNamespaceUri;
            byte[] acName;
            byte[] acValueStr;
            byte[] acType;
            byte[] acData;

            private int getNamespaceUriValue()
            {
                return Common.byte2Int(acNamespaceUri);
            }

            private int getNameValue()
            {
                return Common.byte2Int(acName);
            }

            private int getValueStrValue()
            {
                return Common.byte2Int(acValueStr);
            }

            private int getTypeValue()
            {
                return Common.byte2Int(acType);
            }

            private int getDataValue()
            {
                return Common.byte2Int(acData);
            }

            @NonNull
            @Override
            public String toString()
            {
                StringBuilder builder = new StringBuilder();
                // 当没有android这样的前缀的时候，NamespaceUri是null
                builder.append("NamespaceUri: ").append(Common.byte2HexString(acNamespaceUri)).append(" (");
                builder.append((getNamespaceUriValue() != -1 && getNamespaceUriValue() < mStringChunk.scStringPoolContentList.size()) ? mStringChunk.scStringPoolContentList.get(getNamespaceUriValue()) : "null");
                builder.append("), ");

                builder.append("AttributeName: ").append(Common.byte2HexString(acName)).append(" (");
                builder.append((getNameValue() != -1 && getNameValue() < mStringChunk.scStringPoolContentList.size()) ? mStringChunk.scStringPoolContentList.get(getNameValue()) : "null");
                builder.append("), ");

                builder.append("ValueStr: ").append(Common.byte2HexString(acValueStr)).append(" (");
                builder.append((getValueStrValue() != -1 && getValueStrValue() < mStringChunk.scStringPoolContentList.size()) ? mStringChunk.scStringPoolContentList.get(getValueStrValue()) : "null");
                builder.append("), ");

                builder.append("Type: ").append(Common.byte2HexString(acType)).append(" (").append(getTypeValue()).append("),");

                builder.append("Data: ").append(Common.byte2HexString(acData)).append(" (").append(getDataValue()).append(")");
                return builder.toString();
            }
        }
    }

    class EndTagChunk
    {
        private EndTagChunk() {}

        byte[] etcSignature;
        byte[] etcSize;
        byte[] etcLineNumber;
        byte[] etcUNKNOWN;
        byte[] etcNamespaceUri;
        byte[] etcName;

        @NonNull
        @Override
        public String toString()
        {
            return super.toString();
        }
    }
}
