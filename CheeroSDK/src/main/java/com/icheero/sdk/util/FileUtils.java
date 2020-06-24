package com.icheero.sdk.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by 左程耀 on 2018/3/2.
 */

@SuppressWarnings("unused")
public class FileUtils
{
    public static final String DIR_PATH_BASE = Environment.getExternalStorageDirectory().getPath();
    public static final String DIR_PATH_CHEERO_ROOT = DIR_PATH_BASE + "/Cheero";
    public static final String DIR_PATH_CHEERO_IMAGES = DIR_PATH_CHEERO_ROOT + "/images/";
    public static final String DIR_PATH_CHEERO_VIDEOS = DIR_PATH_CHEERO_ROOT + "/videos/";
    public static final String DIR_PATH_CHEERO_AUDIOS = DIR_PATH_CHEERO_ROOT + "/audios/";
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
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED))
        {
            File dir = new File(dirPath);
            if (dir.exists())
                return true;
            return dir.mkdir();
        }
        return false;
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
                while ((len = in.read(buffer)) != -1) out.write(buffer, 0, len);
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
            while ((length = body.read(data)) != -1) outputStream.write(data, 0, length);
            return outputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                outputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
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
            while ((length = body.read(data)) != -1) outputStream.write(data, 0, length);
            return outputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                outputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String saveFile(String fileName, byte[] data)
    {
        if (data == null) return null;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try
        {
            String fullFileName = DIR_PATH_CHEERO_IMAGES + fileName;
            fos = new FileOutputStream(fullFileName);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
            return fullFileName;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    public static ByteOrder byteOrder()
    {
        return ByteOrder.nativeOrder();
    }

    /**
     * 字节数组 转换 十六进制字符串
     */
    public static String byte2HexString(byte... data)
    {
        StringBuilder ret = new StringBuilder();
        if (data != null && data.length > 0)
        {
            for (byte b : data)
            {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) hex = '0' + hex;
                ret.append(hex).append(" ");
            }
            return ret.toString();
        }
        return null;
    }

    public static byte[] copyBytes(byte[] src, int start)
    {
        if (src == null) return null;
        byte[] dest = new byte[src.length - start];
        System.arraycopy(src, start, dest, 0, dest.length);
        return dest;
    }

    public static byte[] copyBytes(byte[] src, int start, int count)
    {
        if (src == null) return null;
        byte[] dest = new byte[count];
        System.arraycopy(src, start, dest, 0, count);
        return dest;
    }

    public static void copyBytes(byte[] src, int start, byte[] dest)
    {
        System.arraycopy(src, start, dest, 0, dest.length);
    }

    public static char[] byte2Char(byte[] data)
    {
        Charset charset = Charset.forName("UTF-8");
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);
        byteBuffer.order(byteOrder()).put(data);
        byteBuffer.flip();
        CharBuffer charBuffer = charset.decode(byteBuffer);
        return charBuffer.array();
    }

    public static byte[] int2Byte(int number)
    {
        byte[] data = new byte[4];
        data[0] = (byte) ((number >> 24) & 0xff);
        data[1] = (byte) ((number >> 16) & 0xff);
        data[2] = (byte) ((number >> 8) & 0xff);
        data[3] = (byte) (number & 0xff);
        return data;
    }

    public static int byte2Int(byte[] data)
    {
        /*
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(byteOrder()).put(data).getInt();
        */
        if (byteOrder() == ByteOrder.LITTLE_ENDIAN) data = reverseBytes(data);
        return data[3] & 0xFF | (data[2] & 0xFF) << 8 | (data[1] & 0xFF) << 16 | (data[0] & 0xFF) << 24;
    }

    public static short byte2Short(byte[] data)
    {
        /*
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(byteOrder());
        byteBuffer.put(data);
        return byteBuffer.getShort(0);
        */
        if (byteOrder() == ByteOrder.LITTLE_ENDIAN) data = reverseBytes(data);
        return (short) (data[1] & 0xff | (data[0] & 0xff) << 8);
    }

    public static byte[] reverseBytes(byte[] bytes)
    {
        if (bytes == null || (bytes.length == 1)) return bytes;
        byte[] newBytes = copyBytes(bytes, 0, bytes.length);
        int offset = bytes.length / 2;
        // 1 2
        for (int i = 0; i < offset; i++)
        {
            byte tmp = newBytes[i];
            newBytes[i] = newBytes[newBytes.length - i - 1];
            newBytes[newBytes.length - i - 1] = tmp;
        }
        return newBytes;
    }
}
