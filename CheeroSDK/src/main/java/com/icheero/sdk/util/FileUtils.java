package com.icheero.sdk.util;

import android.content.Context;
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
            if (!file.exists())
                flag = file.mkdir();
        }
        return flag;
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
     * 根据url，创建文件
     * @param url url地址
     * @return file
     */
    public static File getFileByUrl(Context context, final String url)
    {
        File file = new File(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? context.getExternalCacheDir() : context.getCacheDir(), Common.md5(url));
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

    /**
     * 根据url地址 获取文件名
     */
    public static String getFileName(String url)
    {
        int lastSeparatorIndex = url.lastIndexOf("/");
        return (lastSeparatorIndex < 0) ? url : url.substring(lastSeparatorIndex + 1, url.length());
    }
}
