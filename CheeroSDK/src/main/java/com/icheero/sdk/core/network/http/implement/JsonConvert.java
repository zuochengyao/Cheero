package com.icheero.sdk.core.network.http.implement;

import com.google.gson.Gson;
import com.icheero.sdk.core.network.http.api.CheeroApi;
import com.icheero.sdk.core.network.http.encapsulation.IConvert;

import java.io.Reader;
import java.lang.reflect.Type;

public class JsonConvert implements IConvert
{
    private Gson mGson;

    public JsonConvert()
    {
        this.mGson = new Gson();
    }

    @Override
    public Object parse(Reader reader, Type type)
    {
        return mGson.fromJson(reader, type);
    }

    @Override
    public Object parse(String content, Type type)
    {
        return mGson.fromJson(content, type);
    }

    @Override
    public boolean isSupportParse(String contentType)
    {
        return CheeroApi.MEDIA_TYPE_JSON.equals(contentType);
    }
}
