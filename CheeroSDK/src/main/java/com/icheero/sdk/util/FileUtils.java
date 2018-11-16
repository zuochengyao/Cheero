package com.icheero.sdk.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
            options.inSampleSize = 10; // 降低采样率10倍
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
}
