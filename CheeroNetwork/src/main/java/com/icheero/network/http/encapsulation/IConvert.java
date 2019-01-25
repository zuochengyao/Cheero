package com.icheero.network.http.encapsulation;

import java.io.Reader;
import java.lang.reflect.Type;

public interface IConvert
{
    Object parse(Reader reader, Type type);

    Object parse(String content, Type type);

    boolean isSupportParse(String contentType);
}
