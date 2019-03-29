package com.icheero.sdk.util;

public class XmlUtils
{
    public static String createDeclaration(String version, String encoding)
    {
        return String.format("<?xml version=\"%s\" encoding=\"%s\"?>\n", version, encoding);
    }

    public static String createEndTag(String tagName)
    {
        return "</" + tagName + ">\n";
    }
}
