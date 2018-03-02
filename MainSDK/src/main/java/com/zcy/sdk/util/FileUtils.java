package com.zcy.sdk.util;

/**
 * Created by zuochengyao on 2018/3/2.
 */

public class FileUtils
{
    /**
     * 获取文件名
     */
    public static String getFileName(String url)
    {
        int lastSeparatorIndex = url.lastIndexOf("/");
        return (lastSeparatorIndex < 0) ? url : url.substring(lastSeparatorIndex + 1, url.length());
    }
}
