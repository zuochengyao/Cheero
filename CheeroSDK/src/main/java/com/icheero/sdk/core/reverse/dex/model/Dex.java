package com.icheero.sdk.core.reverse.dex.model;

import java.util.ArrayList;
import java.util.List;

public class Dex
{
    private DexHeader header;
    public List<StringIdItem> stringIds;
    public List<TypeIdItem> typeIds;
    public List<ProtoIdItem> protoIds;
    public List<FieldIdItem> fieldIds;
    public List<MethodIdItem> methodIds;
    public List<ClassDefItem> classDefs;

    public Dex(DexHeader header)
    {
        this.header = header;
        stringIds = new ArrayList<>();
        typeIds = new ArrayList<>();
        protoIds = new ArrayList<>();
        fieldIds = new ArrayList<>();
        methodIds = new ArrayList<>();
        classDefs = new ArrayList<>();
    }

    public DexHeader getHeader()
    {
        return header;
    }
}
