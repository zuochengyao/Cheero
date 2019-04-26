package com.icheero.sdk.core.reverse.dex.model;

/**
 * map_item 结构有 4 个元素：
 *
 * type 表示该 map_item 的类型 ，本节能用到的描述如下 ，详细Dalvik Executable Format 里 Type Code 的定义
 * unused 是用对齐字节的，无实际用处
 * size 表示再细分此 item ，该类型的个数
 * offset 是第一个元素的针对文件初始位置的偏移量
 *
 * struct map_item
 * {
 *     ushort type;
 *     ushort unuse;
 *     uint size;
 *     uint offset;
 * }
 */
class MapItem
{
    public byte[] type = new byte[2];
    public byte[] unused = new byte[2];
    public byte[] size = new byte[4];
    public byte[] offset = new byte[4];

    public static int getLength()
    {
        return 12;
    }
}
