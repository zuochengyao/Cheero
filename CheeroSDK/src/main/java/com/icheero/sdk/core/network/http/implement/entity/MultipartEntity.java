package com.icheero.sdk.core.network.http.implement.entity;

import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

public class MultipartEntity extends AbstractHttpEntity<Object>
{
    public void addBinaryPart(String key, byte[] data)
    {
        mMap.put(key, data);
    }

    public void addFilePart(String key, File file)
    {
        mMap.put(key, file);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet()
    {
        return mMap.entrySet();
    }

    @Override
    public byte[] getBytes()
    {
        return new byte[0];
    }

    @Override
    public void write(OutputStream out) throws IOException
    {
        
    }
}
