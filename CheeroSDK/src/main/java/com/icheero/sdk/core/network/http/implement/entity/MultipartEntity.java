package com.icheero.sdk.core.network.http.implement.entity;

import android.text.TextUtils;

import com.icheero.sdk.core.network.http.HttpApi;
import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.sdk.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class MultipartEntity extends AbstractHttpEntity
{
    // 换行符
    private final String NEW_LINE = System.lineSeparator();
    private final String DASH_DASH = "--";
    private final String HEADER_CONTENT_TYPE = "Content-Type: ";
    private final String HEADER_CONTENT_LENGTH = "Content-Length: ";
    private final String CONTENT_DISPOSITION = "Content-Disposition: ";
    // 二进制参数
    private final String ENCODING_BINARY = "Content-Transfer-Encoding: binary";
    // 文本参数
    private final String ENCODING_BIT = "Content-Transfer-Encoding: 8bit";

    private ByteArrayOutputStream mOutputStream;

    public MultipartEntity()
    {
        mOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public void addString(String key, String value)
    {
        try
        {
            super.addString(key, value);
            write(ENCODING_BIT, HttpApi.MEDIA_TYPE_TEXT, value.getBytes(HttpApi.ENCODING_UTF8), key, null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addBinaryPart(String key, byte[] data)
    {
        if (data == null)
            return;
        try
        {
            mMap.put(key, data);
            write(ENCODING_BINARY, HttpApi.MEDIA_TYPE_MULTIPART, data, key, key);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addFilePart(String key, File file)
    {
        if (file == null)
            return;
        try
        {
            mMap.put(key, file);
            write(ENCODING_BINARY, HttpApi.MEDIA_TYPE_MULTIPART, FileUtils.getFileBytes(file), key, file.getName());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getBytes() throws IOException
    {
        mOutputStream.write((DASH_DASH + mBoundary + DASH_DASH + NEW_LINE).getBytes());
        return mOutputStream.toByteArray();
    }

    private void write(String contentEncoding, String contentType, byte[] data, String key, String fileName) throws IOException
    {
        Charset utf8 = Charset.forName(HttpApi.ENCODING_UTF8);
        // boundary line
        mOutputStream.write((DASH_DASH + mBoundary + NEW_LINE).getBytes(utf8));
        // content-disposition line
        mOutputStream.write(getContentDisposition(key, fileName).getBytes(utf8));
        // content-type line
        mOutputStream.write((HEADER_CONTENT_TYPE + contentType + NEW_LINE).getBytes(utf8));
        // content-length line
        mOutputStream.write((HEADER_CONTENT_LENGTH + data.length + NEW_LINE).getBytes(utf8));
        // content-encoding line
        mOutputStream.write((contentEncoding + NEW_LINE).getBytes(utf8));
        // new line
        mOutputStream.write(NEW_LINE.getBytes(utf8));
        // data line
        mOutputStream.write(data);
        // new line
        mOutputStream.write(NEW_LINE.getBytes(utf8));
    }

    private String getContentDisposition(String key, String fileName)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTENT_DISPOSITION + "form-data; name=\"").append(key).append("\"");
        // 文本参数没有key参数,设置为空即可
        if (!TextUtils.isEmpty(fileName))
            stringBuilder.append("; filename=\"").append(fileName).append("\"");
        return stringBuilder.append(NEW_LINE).toString();
    }
}
