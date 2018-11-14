package com.icheero.sdk.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
}
