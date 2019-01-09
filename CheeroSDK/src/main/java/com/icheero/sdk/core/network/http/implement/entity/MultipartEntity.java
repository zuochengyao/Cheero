package com.icheero.sdk.core.network.http.implement.entity;

import android.text.TextUtils;

import com.icheero.sdk.base.BaseApi;
import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.sdk.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MultipartEntity extends AbstractHttpEntity
{
    //换行符
    private final String NEW_LINE = System.lineSeparator();
    private final String HEADER_CONTENT_TYPE = "Content-Type: ";
    private final String HEADER_CONTENT_LENGTH = "Content-Length: ";
    private final String CONTENT_DISPOSITION = "Content-Disposition: ";
    // 文本参数和字符集
    private final String TYPE_TEXT_CHARSET = "text/plain;charset=UTF-8";
    // 字节流参数
    private final String TYPE_OCTET_STREAM = "application/octet-stream";
    // 二进制参数
    private final byte[] ENCODING_BINARY = "Content-Transfer-Encoding: binary\r\n\r\n".getBytes();
    // 文本参数
    private final byte[] ENCODING_BIT = "Content-Transfer-Encoding: 8bit\r\n\r\n".getBytes();

    private ByteArrayOutputStream mOutputStream;

    public MultipartEntity()
    {
        mOutputStream = new ByteArrayOutputStream();
    }

    public void addBinaryPart(String key, byte[] data)
    {
        mMap.put(key, data);
    }

    public void addFilePart(String key, File file)
    {
        mMap.put(key, file);
    }

    @Override
    public byte[] getBytes() throws IOException
    {
        if (mMap.size() > 0)
        {
            for (Map.Entry<String, Object> entry : mMap.entrySet())
            {
                String key = entry.getKey();
                Object value = entry.getValue();
                mOutputStream.write(("--" + mBoundary + "\r\n").getBytes());
                if (value instanceof String)
                    write(null, ENCODING_BIT, ((String) value).getBytes(), key, null);
                else if (value instanceof byte[])
                    write(BaseApi.MEDIA_TYPE_MULTIPART, ENCODING_BINARY, (byte[]) value, key, key);
                else if (value instanceof File)
                {
                    File file = (File) value;
                    write(BaseApi.MEDIA_TYPE_MULTIPART, ENCODING_BINARY, FileUtils.getFileBytes(file), key, file.getName());
                }
            }
            mOutputStream.write(("--" + mBoundary + "--" + "\r\n").getBytes());
        }
        return mOutputStream.toByteArray();
    }

    private void write(String contentType, byte[] contentEncoding, byte[] data, String key, String fileName) throws IOException
    {
        mOutputStream.write(getContentDispositionBytes(key, fileName));
        if (!TextUtils.isEmpty(contentType))
            mOutputStream.write((HEADER_CONTENT_TYPE + contentType + NEW_LINE).getBytes());
        mOutputStream.write((HEADER_CONTENT_LENGTH + data.length + NEW_LINE + NEW_LINE).getBytes());
//        mOutputStream.write(contentEncoding);
        mOutputStream.write(data);
        mOutputStream.write(NEW_LINE.getBytes());
    }

    private byte[] getContentDispositionBytes(String key, String fileName)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTENT_DISPOSITION + "form-data; name=\"").append(key).append("\"");
        // 文本参数没有key参数,设置为空即可
        if (!TextUtils.isEmpty(fileName))
            stringBuilder.append("; filename=\"").append(fileName).append("\"");
        return stringBuilder.append(NEW_LINE).toString().getBytes();
    }
}
