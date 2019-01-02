package com.icheero.sdk.core.network.http.implement.entity;

import com.icheero.sdk.base.BaseApi;
import com.icheero.sdk.core.network.http.encapsulation.AbstractHttpEntity;
import com.icheero.sdk.util.Common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

public class FormEntity extends AbstractHttpEntity<String>
{
    @Override
    public Set<Map.Entry<String, String>> entrySet()
    {
        return mMap.entrySet();
    }

    @Override
    public byte[] getBytes()
    {
        return mMap.size() > 0 ? Common.encodeParam(mMap, BaseApi.ENCODING_UTF8) : null;
    }

    @Override
    public void write(OutputStream out) throws IOException
    {

    }
}
