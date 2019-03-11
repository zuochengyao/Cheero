package com.icheero.sdk.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 左程耀 on 2018/3/2.
 */

@SuppressWarnings("unused")
public class FileUtils
{
    public static final String DIR_PATH_BASE = Environment.getExternalStorageDirectory().getPath();
    public static final String DIR_PATH_CHEERO_ROOT = DIR_PATH_BASE + "/Cheero";
    public static final String DIR_PATH_CHEERO_IMAGES = DIR_PATH_CHEERO_ROOT + "/images/";
    public static final String DIR_PATH_CHEERO_LOGS = DIR_PATH_CHEERO_ROOT + "/logs/";
    public static final String DIR_PATH_CHEERO_PATCHES = DIR_PATH_CHEERO_ROOT + "/patches/";
    public static final String DIR_PATH_CHEERO_CACHE = DIR_PATH_CHEERO_ROOT + "/cache/";

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

    /**
     * 获取文件数据流
     * @param file
     */
    public static byte[] getFileBytes(File file)
    {
        byte[] data = null;
        if (file != null && file.exists())
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try
            {
                InputStream in = new FileInputStream(file);
                byte[] buffer = new byte[4 * 1024];
                int len;
                while ((len = in.read(buffer)) != -1)
                    out.write(buffer, 0, len);
                data = out.toByteArray();
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 获取raw资源文件数据
     */
    public static byte[] readRawResource(Context context, int rid)
    {
        InputStream in = context.getResources().openRawResource(rid);
        return getInputStreamData(in);
    }

    /**
     * 获取响应InputStream数据
     */
    public static byte[] getInputStreamData(InputStream body, long contentLength)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) contentLength);
        int length;
        byte[] data = new byte[1024];
        try
        {
            while ((length = body.read(data)) != -1)
                outputStream.write(data, 0, length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    /**
     * 获取响应InputStream数据
     */
    public static byte[] getInputStreamData(InputStream body)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length;
        byte[] data = new byte[1024];
        try
        {
            while ((length = body.read(data)) != -1)
                outputStream.write(data, 0, length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
