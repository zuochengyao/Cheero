package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * map_list 里先用一个 uint 描述后面有 size 个 map_item , 后续就是对应的 size 个 map_item 描述
 */
public class MapList
{
    public byte[] size = new byte[4];
    public List<MapItem> list;

    public MapList()
    {
        list = new ArrayList<>();
    }

    public int getSizeValue()
    {
        return FileUtils.byte2Int(size);
    }
}
