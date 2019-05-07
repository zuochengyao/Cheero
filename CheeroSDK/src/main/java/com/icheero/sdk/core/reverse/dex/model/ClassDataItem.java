package com.icheero.sdk.core.reverse.dex.model;

import androidx.annotation.NonNull;

public class ClassDataItem
{
    public Uleb128 staticFieldsSize;
    public Uleb128 instanceFieldsSize;
    public Uleb128 directMethodsSize;
    public Uleb128 virtualMethodsSize;

    public EncodedField[] staticFields;
    public EncodedField[] instanceFields;
    public EncodedMethod[] directMethods;
    public EncodedMethod[] virtualMethods;

    private String getFieldsAndMethods()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("static_fields:\n");
        for (EncodedField staticField : staticFields)
            sb.append(staticField).append("\n");

        sb.append("instance_fields:\n");
        for (EncodedField instanceField : instanceFields)
            sb.append(instanceField).append("\n");

        sb.append("direct_methods:\n");
        for (EncodedMethod directMethod : directMethods)
            sb.append(directMethod).append("\n");

        sb.append("virtual_methods:\n");
        for (EncodedMethod virtualMethod : virtualMethods)
            sb.append(virtualMethod).append("\n");
        return sb.toString();
    }

    @NonNull
    @Override
    public String toString()
    {
        return "static_fields_size:"+staticFieldsSize.asLong()+
                ",instance_fields_size:"+instanceFieldsSize.asLong()+
                ",direct_methods_size:"+directMethodsSize.asLong()+
                ",virtual_methods_size:"+virtualMethodsSize.asLong()+"\n"+
                getFieldsAndMethods();
    }
}
