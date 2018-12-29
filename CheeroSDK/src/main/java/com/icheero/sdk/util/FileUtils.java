package com.icheero.sdk.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 左程耀 on 2018/3/2.
 */

@SuppressWarnings("unused")
public class FileUtils
{
    public static Bitmap convertToBitmap(File file)
    {
        Bitmap bitmap = null;
        try
        {
            FileInputStream in = new FileInputStream(file);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // options.inSampleSize = 10; // 降低采样率10倍
            bitmap = BitmapFactory.decodeStream(in, null, options);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 创建目录
     * @param dirPath 目录路径
     * @return 是否成功
     */
    public static boolean createDir(String dirPath)
    {
        boolean flag = false;
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED))
        {
            File file = new File(dirPath);
            if (!file.exists()) flag = file.mkdir();
        }
        return flag;
    }

    /**
     * 创建文件
     * @param filePath 文件路径
     * @return file
     */
    public static File createFile(String filePath)
    {
        File file = new File(filePath);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static boolean deleteFile(String filePath)
    {
        return new File(filePath).delete();
    }

    public static boolean exists(String filePath)
    {
        return new File(filePath).exists();
    }

    /**
     * 根据url地址 获取文件名
     */
    public static String getFileName(String url)
    {
        int lastSeparatorIndex = url.lastIndexOf("/");
        return (lastSeparatorIndex < 0) ? url : url.substring(lastSeparatorIndex + 1, url.length());
    }
}
