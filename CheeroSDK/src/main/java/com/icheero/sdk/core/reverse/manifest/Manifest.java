package com.icheero.sdk.core.reverse.manifest;

import com.icheero.sdk.util.FileUtils;

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
    private ArrayList<StartNamespaceChunk> mStartNamespaceChunkList;
    private ArrayList<EndNamespaceChunk> mEndNamespaceChunkList;
    private ArrayList<StartTagChunk> mStartTagChunkList;
    private ArrayList<EndTagChunk> mEndTagChunkList;

    Manifest()
    {
        mHeader = new Header();
        mStringChunk = new StringChunk();
        mResourceIdChunk = new ResourceIdChunk();
        mStartNamespaceChunkList = new ArrayList<>();
        mEndNamespaceChunkList = new ArrayList<>();
        mStartTagChunkList = new ArrayList<>();
        mEndTagChunkList = new ArrayList<>();
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

    ArrayList<StartNamespaceChunk> getStartNamespaceChunkList()
    {
        return mStartNamespaceChunkList;
    }

    ArrayList<EndNamespaceChunk> getEndNamespaceChunkList()
    {
        return mEndNamespaceChunkList;
    }

    ArrayList<StartTagChunk> getStartTagChunkList()
    {
        return mStartTagChunkList;
    }

    ArrayList<EndTagChunk> getEndTagChunkList()
    {
        return mEndTagChunkList;
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
            return FileUtils.byte2Int(hMagic);
        }

        /**
         * @return Manifest 总长度
         */
        int getSizeValue()
        {
            return FileUtils.byte2Int(hSize);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ Header ------------------\n");
            builder.append("MagicNumber: ").append(FileUtils.byte2HexString(hMagic)).append("(").append(getMagicNumberValue()).append(")").append("\n");
            builder.append("Size: ").append(FileUtils.byte2HexString(hSize)).append("(").append(getSizeValue()).append(")");
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
            return FileUtils.byte2Int(scSignature);
        }

        int getSizeValue()
        {
            return FileUtils.byte2Int(scSize);
        }

        int getStringCountValue()
        {
            return FileUtils.byte2Int(scStringCount);
        }

        int getStyleCountValue()
        {
            return FileUtils.byte2Int(scStyleCount);
        }

        int getUnknownValue()
        {
            return FileUtils.byte2Int(scUnknown);
        }

        int getStringPoolOffsetValue()
        {
            return FileUtils.byte2Int(scStringPoolOffset);
        }

        int getStylePoolOffsetValue()
        {
            return FileUtils.byte2Int(scStylePoolOffset);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ StringChunk ------------------\n");
            builder.append("Signature: ").append(FileUtils.byte2HexString(scSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(FileUtils.byte2HexString(scSize)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("String Count: ").append(FileUtils.byte2HexString(scStringCount)).append("(").append(getStringCountValue()).append(")").append("\n");
            builder.append("Style Count: ").append(FileUtils.byte2HexString(scStyleCount)).append("(").append(getStyleCountValue()).append(")").append("\n");
            builder.append("Unknown: ").append(FileUtils.byte2HexString(scUnknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("StringPool Offset: ").append(FileUtils.byte2HexString(scStringPoolOffset)).append("(").append(getStringPoolOffsetValue()).append(")").append("\n");
            builder.append("StylePool Offset: ").append(FileUtils.byte2HexString(scStylePoolOffset)).append("(").append(getStylePoolOffsetValue()).append(")").append("\n");
            for (int i = 0; i < scStringPoolContentList.size(); i++)
            {
                builder.append("str[").append(i).append("]: ").append(scStringPoolContentList.get(i));
                if (i != scStringPoolContentList.size() - 1) builder.append("\n");
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
            return FileUtils.byte2Int(rcSignature);
        }

        int getSizeValue()
        {
            return FileUtils.byte2Int(rcSize);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ ResourceIdChunk ------------------\n");
            builder.append("Signature: ").append(FileUtils.byte2HexString(rcSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(FileUtils.byte2HexString(rcSize)).append("(").append(getSizeValue()).append(")").append("\n");
            for (int i = 0; i < rcResourceIdList.size(); i++)
            {
                builder.append("resId[").append(i).append("]: ");
                builder.append(FileUtils.byte2HexString(FileUtils.int2Byte(rcResourceIdList.get(i))));
                builder.append("(").append(rcResourceIdList.get(i)).append(")");
                if (i != rcResourceIdList.size() - 1) builder.append("\n");
            }
            return builder.toString();
        }
    }

    class StartNamespaceChunk
    {
        StartNamespaceChunk() {}

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
            return FileUtils.byte2Int(sncSignature);
        }

        int getSizeValue()
        {
            return FileUtils.byte2Int(sncSize);
        }

        int getLineNumberValue()
        {
            return FileUtils.byte2Int(sncLineNumber);
        }

        int getUnknownValue()
        {
            return FileUtils.byte2Int(sncUnknown);
        }

        int getPrefixValue()
        {
            return FileUtils.byte2Int(sncPrefix);
        }

        String getPrefixStr()
        {
            return mStringChunk.scStringPoolContentList.get(getPrefixValue());
        }

        int getUriValue()
        {
            return FileUtils.byte2Int(sncUri);
        }

        String getUriStr()
        {
            return mStringChunk.scStringPoolContentList.get(getUriValue());
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ StartNamespaceChunk ------------------\n");
            builder.append("Signature: ").append(FileUtils.byte2HexString(sncSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(FileUtils.byte2HexString(sncSize)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("LineNumber: ").append(FileUtils.byte2HexString(sncLineNumber)).append("(").append(getLineNumberValue()).append(")").append("\n");
            builder.append("Unknown: ").append(FileUtils.byte2HexString(sncUnknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("Prefix: ").append(FileUtils.byte2HexString(sncPrefix)).append("(").append(getPrefixValue()).append(")").append("\n");
            builder.append("Prefix: ").append(getPrefixStr()).append("\n");
            builder.append("Uri: ").append(FileUtils.byte2HexString(sncUri)).append("(").append(getUriValue()).append(")").append("\n");
            builder.append("Uri: ").append(getUriStr()).append("\n");
            return builder.toString();
        }
    }

    class EndNamespaceChunk
    {
        EndNamespaceChunk() {}

        /** Chunk的类型，固定四个字节：0x00100100 */
        byte[] encSignature;
        /** Chunk的大小 */
        byte[] encSize;
        /** 在AndroidManifest文件中的行号 */
        byte[] encLineNumber;
        /** 未知区域 */
        byte[] encUnknown;
        /** 命名空间的前缀(在字符串中的索引值)，比如：android */
        byte[] encPrefix;
        /** 命名空间的uri(在字符串中的索引值)：比如：http://schemas.android.com/apk/res/android */
        byte[] encUri;

        int getSignatureValue()
        {
            return FileUtils.byte2Int(encSignature);
        }

        int getSizeValue()
        {
            return FileUtils.byte2Int(encSize);
        }

        int getLineNumberValue()
        {
            return FileUtils.byte2Int(encLineNumber);
        }

        int getUnknownValue()
        {
            return FileUtils.byte2Int(encUnknown);
        }

        int getPrefixValue()
        {
            return FileUtils.byte2Int(encPrefix);
        }

        int getUriValue()
        {
            return FileUtils.byte2Int(encUri);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ EndNamespaceChunk ------------------\n");
            builder.append("Signature: ").append(FileUtils.byte2HexString(encSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(FileUtils.byte2HexString(encSize)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("LineNumber: ").append(FileUtils.byte2HexString(encLineNumber)).append("(").append(getLineNumberValue()).append(")").append("\n");
            builder.append("Unknown: ").append(FileUtils.byte2HexString(encUnknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("Prefix: ").append(FileUtils.byte2HexString(encPrefix)).append("(").append(getPrefixValue()).append(")").append("\n");
            builder.append("Prefix: ").append(mStringChunk.scStringPoolContentList.get(getPrefixValue())).append("\n");
            builder.append("Uri: ").append(FileUtils.byte2HexString(encUri)).append("(").append(getUriValue()).append(")").append("\n");
            builder.append("Uri: ").append(mStringChunk.scStringPoolContentList.get(getUriValue())).append("\n");
            return builder.toString();
        }
    }

    public class StartTagChunk
    {
        StartTagChunk() {}

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
        public ArrayList<AttributeChunk> stcAttributeList;

        int getSignatureValue()
        {
            return FileUtils.byte2Int(stcSignature);
        }

        int getSizeValue()
        {
            return FileUtils.byte2Int(stcSize);
        }

        int getLineNumberValue()
        {
            return FileUtils.byte2Int(stcLineNumber);
        }

        int getUnknownValue()
        {
            return FileUtils.byte2Int(stcUnknown);
        }

        int getNamespaceUriValue()
        {
            return FileUtils.byte2Int(stcNamespaceUri);
        }

        String getNamespaceUriStr()
        {
            return mStringChunk.scStringPoolContentList.get(getNamespaceUriValue());
        }

        int getNameValue()
        {
            return FileUtils.byte2Int(stcName);
        }

        String getNameStr()
        {
            return mStringChunk.scStringPoolContentList.get(getNameValue());
        }

        int getFlagsValue()
        {
            return FileUtils.byte2Int(stcFlags);
        }

        int getAttributeCountValue()
        {
            return FileUtils.byte2Int(stcAttributeCount);
        }

        int getClassAttributeValue()
        {
            return FileUtils.byte2Int(stcClassAttribute);
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ StartTagChunk ------------------\n");
            builder.append("Signature: ").append(FileUtils.byte2HexString(stcSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(FileUtils.byte2HexString(stcSize)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("LineNumber: ").append(FileUtils.byte2HexString(stcLineNumber)).append("(").append(getLineNumberValue()).append(")").append("\n");
            builder.append("Unknown: ").append(FileUtils.byte2HexString(stcUnknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("NamespaceUri: ").append(FileUtils.byte2HexString(stcNamespaceUri)).append("(").append(getNamespaceUriValue()).append(")").append("\n");
            if (getNamespaceUriValue() != -1 && getNamespaceUriValue() < mStringChunk.scStringPoolContentList.size())
                builder.append("Uri Str: ").append(getNamespaceUriStr()).append("\n");
            builder.append("TagName: ").append(FileUtils.byte2HexString(stcName)).append("(").append(getNameValue()).append(")").append("\n");
            if (getNameValue() != -1 && getNameValue() < mStringChunk.scStringPoolContentList.size())
                builder.append("TagName Str: ").append(getNameStr()).append("\n");
            builder.append("Flags: ").append(FileUtils.byte2HexString(stcFlags)).append("(").append(getFlagsValue()).append(")").append("\n");
            builder.append("AttributeCount: ").append(FileUtils.byte2HexString(stcAttributeCount)).append("(").append(getAttributeCountValue()).append(")").append("\n");
            builder.append("ClassAttribute: ").append(FileUtils.byte2HexString(stcClassAttribute)).append("(").append(getClassAttributeValue()).append(")").append("\n");
            for (int i = 0; i < stcAttributeList.size(); i++)
            {
                builder.append("Attribute[").append(i).append("]: ");
                builder.append(stcAttributeList.get(i).toString());
                if (i != stcAttributeList.size() - 1) builder.append("\n");
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
                return FileUtils.byte2Int(acNamespaceUri);
            }

            String getNamespaceUriStr()
            {
                return (getNamespaceUriValue() != -1 && getNamespaceUriValue() < mStringChunk.scStringPoolContentList.size()) ? mStringChunk.scStringPoolContentList.get(getNamespaceUriValue()) : "";
            }

            private int getNameValue()
            {
                return FileUtils.byte2Int(acName);
            }

            String getNameStr()
            {
                return (getNameValue() != -1 && getNameValue() < mStringChunk.scStringPoolContentList.size()) ? mStringChunk.scStringPoolContentList.get(getNameValue()) : "";
            }

            private int getValueStrValue()
            {
                return FileUtils.byte2Int(acValueStr);
            }

            String getValueStr()
            {
                return (getValueStrValue() != -1 && getValueStrValue() < mStringChunk.scStringPoolContentList.size()) ? mStringChunk.scStringPoolContentList.get(getValueStrValue()) : "";
            }

            private int getTypeValue()
            {
                return FileUtils.byte2Int(acType) >> 24;
            }

            private int getDataValue()
            {
                return FileUtils.byte2Int(acData);
            }

            String getDataStr()
            {
                return AttributeType.getAttributeData(mStringChunk.scStringPoolContentList, getTypeValue(), getDataValue());
            }

            @NonNull
            @Override
            public String toString()
            {
                StringBuilder builder = new StringBuilder();
                // 当没有android这样的前缀的时候，NamespaceUri是null
                builder.append("NamespaceUri: ").append(FileUtils.byte2HexString(acNamespaceUri)).append(" (");
                builder.append(getNamespaceUriStr());
                builder.append("), ");

                builder.append("AttributeName: ").append(FileUtils.byte2HexString(acName)).append(" (");
                builder.append(getNameStr());
                builder.append("), ");

                builder.append("ValueStr: ").append(FileUtils.byte2HexString(acValueStr)).append(" (");
                builder.append(getValueStr());
                builder.append("), ");

                builder.append("Type: ").append(FileUtils.byte2HexString(acType)).append(" (").append(AttributeType.getAttrType(getTypeValue())).append("),");
                builder.append("Data: ")
                       .append(FileUtils.byte2HexString(acData))
                       .append("(")
                       .append(getDataStr())
                       .append(")");
                return builder.toString();
            }
        }
    }

    class EndTagChunk
    {
        EndTagChunk() {}

        byte[] etcSignature;
        byte[] etcSize;
        byte[] etcLineNumber;
        byte[] etcUnknown;
        byte[] etcNamespaceUri;
        byte[] etcName;

        int getSignatureValue()
        {
            return FileUtils.byte2Int(etcSignature);
        }

        int getSizeValue()
        {
            return FileUtils.byte2Int(etcSize);
        }

        int getLineNumberValue()
        {
            return FileUtils.byte2Int(etcLineNumber);
        }

        int getUnknownValue()
        {
            return FileUtils.byte2Int(etcUnknown);
        }

        int getNamespaceUriValue()
        {
            return FileUtils.byte2Int(etcNamespaceUri);
        }

        String getNamespaceUriStr()
        {
            return (getNamespaceUriValue() != -1 && getNamespaceUriValue() < mStringChunk.scStringPoolContentList.size()) ? mStringChunk.scStringPoolContentList.get(getNamespaceUriValue()) : "";
        }

        int getNameValue()
        {
            return FileUtils.byte2Int(etcName);
        }

        String getNameStr()
        {
            return (getNameValue() != -1 && getNameValue() < mStringChunk.scStringPoolContentList.size()) ? mStringChunk.scStringPoolContentList.get(getNameValue()) : "";
        }

        @NonNull
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder("------------------ EndTagChunk ------------------\n");
            builder.append("Signature: ").append(FileUtils.byte2HexString(etcSignature)).append("(").append(getSignatureValue()).append(")").append("\n");
            builder.append("Size: ").append(FileUtils.byte2HexString(etcSize)).append("(").append(getSizeValue()).append(")").append("\n");
            builder.append("LineNumber: ").append(FileUtils.byte2HexString(etcLineNumber)).append("(").append(getLineNumberValue()).append(")").append("\n");
            builder.append("Unknown: ").append(FileUtils.byte2HexString(etcUnknown)).append("(").append(getUnknownValue()).append(")").append("\n");
            builder.append("NamespaceUri: ").append(FileUtils.byte2HexString(etcNamespaceUri)).append("(").append(getNamespaceUriStr()).append(")").append("\n");
            builder.append("TagName: ").append(FileUtils.byte2HexString(etcName)).append("(").append(getNameStr()).append(")").append("\n");
            return builder.toString();
        }
    }
}