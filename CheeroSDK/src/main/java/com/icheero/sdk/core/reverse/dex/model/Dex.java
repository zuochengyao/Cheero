package com.icheero.sdk.core.reverse.dex.model;

public class Dex
{
    private DexHeader header;

    public Dex(DexHeader header)
    {
        this.header = header;
    }

    public DexHeader getHeader()
    {
        return header;
    }
}
