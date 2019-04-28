package com.icheero.sdk.core.reverse.dex.model;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class TypeItemList
{
    public int size;
    public List<TypeItem> list = new ArrayList<>();

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        if (size > 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                builder.append(list.get(i).toString());
                if (i < size - 1)
                    builder.append(", ");
            }
        }
        return builder.toString();
    }
}
