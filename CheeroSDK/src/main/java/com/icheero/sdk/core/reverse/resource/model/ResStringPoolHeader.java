package com.icheero.sdk.core.reverse.resource.model;

import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

/**
 * @author zcy 2019-04-01 15:42:13
 *
 * 资源项的值字符串资源池
 * 包含了所有的在资源包里面所定义的资源项的值字符串
 *
 * android-9.0.0_r3/frameworks/base/libs/androidfw/include/androidfw/ResourceTypes.h
 *
 * Definition for a pool of strings.  The data of this chunk is an
 * array of uint32_t providing indices into the pool, relative to
 * stringsStart.  At stringsStart are all of the UTF-16 strings
 * concatenated together; each starts with a uint16_t of the string's
 * length and each ends with a 0x0000 terminator.  If a string is >
 * 32767 characters, the high bit of the length is set meaning to take
 * those 15 bits as a high word and it will be followed by another
 * uint16_t containing the low word.
 *
 * If styleCount is not zero, then immediately following the array of
 * uint32_t indices into the string table is another array of indices
 * into a style table starting at stylesStart.  Each entry in the
 * style table is an array of ResStringPool_span structures.
 *
 * struct ResStringPool_header
 * {
 *     struct ResChunk_header header;
 *
 *     // Number of strings in this pool (number of uint32_t indices that follow
 *     // in the data).
 *     uint32_t stringCount;
 *
 *     // Number of style span arrays in the pool (number of uint32_t indices
 *     // follow the string indices).
 *     uint32_t styleCount;
 *
 *     // Flags.
 *     enum {
 *         // If set, the string index is sorted by the string values (based
 *         // on strcmp16()).
 *         SORTED_FLAG = 1<<0,
 *
 *         // String pool is encoded in UTF-8
 *         UTF8_FLAG = 1<<8
 *     };
 *     uint32_t flags;
 *
 *     // Index from header of the string data.
 *     uint32_t stringsStart;
 *
 *     // Index from header of the style data.
 *     uint32_t stylesStart;
 * };
 */
@SuppressWarnings("StringBufferReplaceableByString")
public class ResStringPoolHeader
{

    public static final int UTF16_FLAG = 0x000;
    public static final int SORTED_FLAG = 0x001;
    public static final int UTF8_FLAG = 0x100; // (1 << 8)

    /** Chunk头部信息格式 */
    public ResChunkHeader header;
    /** 字符串的个数 */
    public byte[] stringCount = new byte[4];
    /** 字符串样式的个数 */
    public byte[] styleCount = new byte[4];
    /**
     * 字符串的属性
     * 可取值包括0x000(UTF-16)、0x001(字符串经过排序)、0x100(UTF-8)和他们的组合值
     */
    public byte[] flags = new byte[4];
    /** 字符串内容块相对于其头部的距离 */
    public byte[] stringsStart = new byte[4];
    /** 字符串样式块相对于其头部的距离 */
    public byte[] stylesStart = new byte[4];

    public int getHeaderSize()
    {
        return ResChunkHeader.getHeaderLength() + 4 + 4 + 4 + 4 + 4;
    }

    int getStringCountValue()
    {
        return FileUtils.byte2Int(stringCount);
    }

    int getStyleCountValue()
    {
        return FileUtils.byte2Int(styleCount);
    }

    int getFlagsValue()
    {
        return FileUtils.byte2Int(flags);
    }

    public String getFlagsValueEncode()
    {
        String encoding = "utf-8";
        if (getFlagsValue() == 0x000)
            encoding = "utf-16";
        return encoding;
    }

    int getStringsStartValue()
    {
        return FileUtils.byte2Int(stringsStart);
    }

    int getStylesStartValue()
    {
        return FileUtils.byte2Int(stylesStart);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("------------------ ResStringPoolHeader ------------------\n");
        builder.append("Header: ").append("\n").append(header.toString()).append("\n");
        builder.append("StringCount: ").append(FileUtils.byte2HexString(stringCount)).append("(").append(getStringCountValue()).append(")").append("\n");
        builder.append("styleCount: ").append(FileUtils.byte2HexString(styleCount)).append("(").append(getStyleCountValue()).append(")").append("\n");
        builder.append("flags: ").append(FileUtils.byte2HexString(flags)).append("(").append(getFlagsValue()).append(")").append("\n");
        builder.append("stringStart: ").append(FileUtils.byte2HexString(stringsStart)).append("(").append(getStringsStartValue()).append(")").append("\n");
        builder.append("stylesStart: ").append(FileUtils.byte2HexString(stylesStart)).append("(").append(getStylesStartValue()).append(")").append("\n");
        return builder.toString();
    }
}
